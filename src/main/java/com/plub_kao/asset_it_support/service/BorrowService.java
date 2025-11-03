package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
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
    public BorrowResponse createBorrow(BorrowRequest request) {


        BorrowRequest.EmployeeRequest EmployeeRequest = request.getEmployeeRequest();
        Employee employee;
        if (EmployeeRequest.getEmployeeId() != null) {
            employee = employeeRepository.findById(EmployeeRequest.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบผู้ยืม"));
        } else {

            employee = new Employee();
            employee.setFirstName(EmployeeRequest.getFirstName());
            employee.setLastName(EmployeeRequest.getLastName());
            employee.setEmail(EmployeeRequest.getEmail());
            employee.setPhone(EmployeeRequest.getPhone());

            if (EmployeeRequest.getRoleId() != null) {
                Role role = roleRepository.findById(EmployeeRequest.getRoleId())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found"));
                employee.setRole(role);
            }
            employee.setDescription(EmployeeRequest.getDescription());

            Department department = departmentRepository.findById(EmployeeRequest.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบแผนก ID: " + EmployeeRequest.getDepartmentId()));
            employee.setDepartment(department);


            employee = employeeRepository.save(employee);
        }


        Borrow borrow = new Borrow();
        borrow.setEmployee(employee);
        borrow.setBorrowDate(request.getBorrowDate() != null ? request.getBorrowDate() : LocalDate.now());
        borrow.setReferenceDoc(request.getReferenceDoc());

        borrow.setBorrowStatus(borrowStatusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("ไม่พบสถานะยืม")));


        Borrow savedBorrow = borrowRepository.save(borrow);


        EquipmentStatus borrowedStatus = equipmentStatusRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("ไม่พบสถานะอุปกรณ์"));

        List<BorrowEquipment> borrowEquipments = new ArrayList<>();
        for (Integer equipmentId : request.getEquipmentId()) {

            Equipment equipment = equipmentRepository.findById(equipmentId)
                    .orElseThrow(() -> new RuntimeException("ไม่พบอุปกรณ์ ID: " + equipmentId));


            Integer status = equipment.getEquipmentStatus().getId();
            if (!Objects.equals(status, 1)) {
                throw new RuntimeException("อุปกรณ์ " + equipment.getEquipmentName() + " ไม่พร้อมให้ยืม");
            }

            BorrowEquipment BorrowEquipment = new BorrowEquipment();
            BorrowEquipment.setBorrow(savedBorrow);
            BorrowEquipment.setEquipment(equipment);
            BorrowEquipment.setDueDate(request.getDueDate());
            borrowEquipments.add(BorrowEquipment);
            equipment.setEquipmentStatus(borrowedStatus);


            equipmentRepository.save(equipment);
        }

        borrowEquipmentRepository.saveAll(borrowEquipments);


        return BorrowResponse.from(savedBorrow, employee, request);
    }


}
