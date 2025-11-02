package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.role.Role;
import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.BorrowResponse;
import com.plub_kao.asset_it_support.entity.borrow.NewBorrow;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;

import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.employee.EmployeeRequest;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private BorrowStatusRepository borrowStatusRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BorrowEquipmentRepository borrowEquipmentRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EquipmentStatusRepository equipmentStatusRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;


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
    public BorrowResponse createBorrow(NewBorrow request) {


        System.out.println(">>> Create borrow called at: " + LocalDateTime.now());
        // 1️⃣ ตรวจว่ามี employeeId ไหม
        Employee employee;

        if (request.getEmployeeId() != null) {
            // กรณีใช้พนักงานที่มีอยู่แล้ว
            employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบผู้ยืม"));
        } else {
            // กรณีสร้างพนักงานใหม่
            employee = new Employee();
            employee.setFirstName(request.getFirstName());
            employee.setLastName(request.getLastName());
            employee.setEmail(request.getEmail());
            employee.setPhone(request.getPhone());
            employee.setDescription(request.getDescription());

            if (request.getRoleId() != null) {
                Role role = roleRepository.findById(request.getRoleId())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found"));
                employee.setRole(role);
            }

            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบแผนก ID: " + request.getDepartmentId()));
            employee.setDepartment(department);

            employee = employeeRepository.save(employee);
        }

        // 2️⃣ สร้าง Borrow
        Borrow borrow = new Borrow();
        borrow.setEmployee(employee);
        borrow.setReferenceDoc(request.getReferenceDoc());
        borrow.setBorrowDate(request.getBorrowDate() != null ? request.getBorrowDate() : LocalDate.now());
        borrow.setBorrowStatus(borrowStatusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("ไม่พบสถานะยืม")));

        Borrow savedBorrow = borrowRepository.save(borrow);

        // 3️⃣ ดึงสถานะอุปกรณ์
        EquipmentStatus borrowedStatus = equipmentStatusRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("ไม่พบสถานะอุปกรณ์"));

        // 4️⃣ วนเพิ่ม borrow_equipment
        List<BorrowEquipment> borrowEquipments = new ArrayList<>();

        for (Integer equipmentId : request.getEquipmentId()) {
            System.out.println("Checking equipment ID: " + equipmentId);
            Equipment equipment = equipmentRepository.findById(equipmentId)
                    .orElseThrow(() -> new RuntimeException("ไม่พบอุปกรณ์ ID: " + equipmentId));

            if (equipment.getEquipmentStatus().getId() != 1) {
                throw new RuntimeException("อุปกรณ์ " + equipment.getEquipmentName() + " ไม่พร้อมให้ยืม");
            }

            BorrowEquipment be = new BorrowEquipment();
            be.setBorrow(savedBorrow);
            be.setEquipment(equipment);
            be.setDueDate(request.getDueDate());
            borrowEquipments.add(be);

            equipment.setEquipmentStatus(borrowedStatus);
            equipmentRepository.save(equipment);
        }

        borrowEquipmentRepository.saveAll(borrowEquipments);

        // 5️⃣ สร้าง Response
        BorrowResponse response = new BorrowResponse();
        response.setReferenceDoc(savedBorrow.getReferenceDoc());
        response.setBorrowDate(savedBorrow.getBorrowDate());
        response.setBorrowStatus(savedBorrow.getBorrowStatus().getId());
        response.setEquipmentId(request.getEquipmentId());

        EmployeeRequest empRes = new EmployeeRequest();
        empRes.setFirstName(employee.getFirstName());
        empRes.setLastName(employee.getLastName());
        empRes.setEmail(employee.getEmail());
        empRes.setPhone(employee.getPhone());
        empRes.setDescription(employee.getDescription());
        response.setEmployeeRequest(empRes);

        return response;
    }


//    public BorrowResponse newBorrow(NewBorrow newBorrow) {
//
//
//        Employee employee = employeeRepository.findByEmployeeId(newBorrow.getEmployee());
//        Borrow borrow = new Borrow(
//                employee,
//                borrowStatusRepository.findByStatusId(1),
//                LocalDate.now(),
//                newBorrow.getReferenceDoc()
//        );
//        Borrow saved = borrowRepository.save(borrow);
//        List<BorrowEquipment> borrowEquipments = new ArrayList<>();
//
//        BorrowEquipmentStatus borrowEquipmentStatus = borrowEquipmentStatusRepository.findById(1);
//
//
//        for (int equipmentId : newBorrow.getEquipmentId()) {
//            Equipment equipment = equipmentRepository.findById(equipmentId)
//                    .orElseThrow(() -> new RuntimeException("error"));
//
//
//            BorrowEquipment be = (new BorrowEquipment(
//                    borrow,
//                    equipment,
//                    borrowEquipmentStatus,
//                    LocalDate.now()
//            ));
//            borrowEquipments.add(be);
//            borrowEquipmentRepository.save(be);
//        }
//        borrow.setBorrowEquipment(borrowEquipments);
//
//        saved = borrowRepository.save(borrow);
//
//        // Map entity -> response DTO
//        BorrowResponse response = new BorrowResponse();
//        response.setId(saved.getId());
//        response.setEmployeeId(saved.getEmployee().getId());
//        response.setEmployeeName(saved.getEmployee().getFirstName());
//        response.setReferenceDoc(saved.getReferenceDoc());
//        response.setBorrowDate(saved.getBorrowDate());
//        response.setStatusName(saved.getBorrowStatus().getBorrowStatusName());
//        return response;
//    }


}
