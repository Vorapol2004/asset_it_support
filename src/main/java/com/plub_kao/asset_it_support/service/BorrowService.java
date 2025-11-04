package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.BorrowStatus;
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
        Employee employee = employeeRepository.findById(request.getEmployeeRequest().getEmployeeId()).orElseThrow();

        BorrowStatus borrowStatus = borrowStatusRepository.findById(1).orElseThrow();

        Borrow borrow = new Borrow();
        borrow.setEmployee(employee);
        borrow.setBorrowStatus(borrowStatus);
        borrow.setBorrowDate(request.getBorrowDate());
        borrow.setReferenceDoc(request.getReferenceDoc());

        List<BorrowEquipment> borrowEquipmentList = new ArrayList<>();
        for (Integer equipmentId : request.getEquipmentIds()) {
            Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();
            equipment.setEquipmentStatus(equipmentStatusRepository.findById(2).orElseThrow());
            equipmentRepository.save(equipment);

            BorrowEquipment borrowEquipment = new BorrowEquipment();
            borrowEquipment.setEquipment(equipment);
            borrowEquipment.setDueDate(request.getDueDate());
            borrowEquipment.setBorrow(borrow);

            borrowEquipmentList.add(borrowEquipment);
        }

        borrow.setBorrowEquipments(borrowEquipmentList);

        return createBorrowResponse(borrowRepository.save(borrow));
    }

    private BorrowResponse createBorrowResponse(Borrow savedBorrow) {
        BorrowResponse response = new BorrowResponse();
        response.setId(savedBorrow.getId());

        Employee employee = savedBorrow.getEmployee();
        response.setEmployeeId(employee.getId());
        response.setEmployeeName(employee.getFirstName() + " " + employee.getLastName());

        response.setReferenceDoc(savedBorrow.getReferenceDoc());
        response.setBorrowDate(savedBorrow.getBorrowDate());
        response.setEquipments(new ArrayList<>());

        List<BorrowEquipment> borrowEquipments = savedBorrow.getBorrowEquipments();
        for (BorrowEquipment borrowEquipment : borrowEquipments) {
            response.addEquipmentInfo(borrowEquipment.getEquipment());
        }

        return response;
    }

}
