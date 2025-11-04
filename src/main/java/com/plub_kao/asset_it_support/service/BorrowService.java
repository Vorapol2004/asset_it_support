package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.role.Role;
import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.BorrowResponse;
import com.plub_kao.asset_it_support.entity.borrow.BorrowRequest;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;

import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class BorrowService {


    private final BorrowRepository borrowRepository;

    private final EquipmentRepository equipmentRepository;

    private final BorrowStatusRepository borrowStatusRepository;

    private final EmployeeRepository employeeRepository;

    private final BorrowEquipmentRepository borrowEquipmentRepository;

    private final EmployeeService employeeService;

    private final EquipmentStatusRepository equipmentStatusRepository;

    private final DepartmentRepository departmentRepository;

    private final RoleRepository roleRepository;


    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // รันทุกวันตอนเที่ยงคืน
    public void updateOverdueBorrowStatus() {
        borrowRepository.updateOverdueStatus();
    }


    //เรียกดูประวัติการยืมของ Borrow ทั้งหมด
    public List<BorrowView> getAllBorrowedEmployeeId() {
        List<BorrowView> borrowAll = borrowRepository.getAllBorrowedEmployeeId();
        return borrowAll;
    }


    //ฟิลเตอร์ Status ของ Borrow ออกมาทั้งหมด
    public List<BorrowView> filterBorrowStatus(@Param("borrowStatusId") Integer borrowStatusId) {
        List<BorrowView> borrowAll = borrowRepository.filterBorrowStatus(borrowStatusId);
        return borrowAll;
    }

    //license_key และ serial_number
    public List<BorrowView> searchBorrowEquipment(@Param("keyword") String keyword) {
        List<BorrowView> borrowAll = borrowRepository.searchBorrowEquipment(keyword);
        return borrowRepository.searchBorrowEquipment(keyword);
    }


    @Transactional
    public BorrowResponse createBorrow(BorrowRequest request) {

        BorrowRequest.EmployeeRequest employeeRequest = request.getEmployeeRequest();
        Employee employee;

        // 🧍‍♂️ 1. หาผู้ยืมหรือเพิ่มใหม่
        if (employeeRequest.getEmployeeId() != null) {
            employee = employeeRepository.findById(employeeRequest.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบผู้ยืม"));
        } else {
            employee = new Employee();
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setEmail(employeeRequest.getEmail());
            employee.setPhone(employeeRequest.getPhone());
            employee.setDescription(employeeRequest.getDescription());

            if (employeeRequest.getRoleId() != null) {
                Role role = roleRepository.findById(employeeRequest.getRoleId())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found"));
                employee.setRole(role);
            }

            Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบแผนก ID: " + employeeRequest.getDepartmentId()));
            employee.setDepartment(department);

            employee = employeeRepository.save(employee);
        }

        // 🧾 2. สร้าง Borrow หลัก
        Borrow borrow = new Borrow();
        borrow.setEmployee(employee);
        borrow.setBorrowDate(request.getBorrowDate() != null ? request.getBorrowDate() : LocalDate.now());
        borrow.setReferenceDoc(request.getReferenceDoc());
        borrow.setBorrowStatus(borrowStatusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("ไม่พบสถานะยืม")));
        Borrow savedBorrow = borrowRepository.save(borrow);


        // ⚙️ 3. ดึงสถานะอุปกรณ์ “ยืมแล้ว”
        EquipmentStatus borrowedStatus = equipmentStatusRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("ไม่พบสถานะอุปกรณ์"));

        // 🧰 4. Loop หาอุปกรณ์จาก serialNumber หรือ licenseKey
        List<BorrowEquipment> borrowEquipments = new ArrayList<>();

        for (BorrowRequest.EquipmentIdentifier identifier : request.getEquipmentIdentifiers()) {
            String keyword = null;

            if (identifier.getSerialNumber() != null && !identifier.getSerialNumber().isBlank()) {
                keyword = identifier.getSerialNumber().trim();
            } else if (identifier.getLicenseKey() != null && !identifier.getLicenseKey().isBlank()) {
                keyword = identifier.getLicenseKey().trim();
            }

            if (keyword == null) {
                throw new RuntimeException("อุปกรณ์ไม่มี serial number หรือ license key");
            }

            System.out.println("🔍 Searching equipment by keyword: " + keyword);

            List<EquipmentView> foundList = equipmentRepository.equipmentIdentifier(keyword);
            if (foundList.isEmpty()) {
                throw new RuntimeException("ไม่พบอุปกรณ์ที่พร้อมใช้งาน: " + keyword);
            }

            EquipmentView foundView = foundList.get(0);

            // ดึง entity ตัวจริงเพื่อตั้งสถานะ
            Equipment equipment = equipmentRepository.findById(foundView.getId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบอุปกรณ์ ID: " + foundView.getId()));

            equipment.setEquipmentStatus(borrowedStatus);
            equipmentRepository.save(equipment);

            // 🧩 ผูก BorrowEquipment
            BorrowEquipment borrowEquipment = new BorrowEquipment();
            borrowEquipment.setBorrow(savedBorrow);
            borrowEquipment.setEquipment(equipment);
            borrowEquipment.setDueDate(request.getDueDate());
            borrowEquipments.add(borrowEquipment);
        }

        // 💾 Save ทั้งหมด
        borrowEquipmentRepository.saveAll(borrowEquipments);


        return BorrowResponse.from(savedBorrow, employee, request);
    }


}
