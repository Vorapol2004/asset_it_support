package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.borrowStatus.BorrowStatus;
import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.BorrowResponse;
import com.plub_kao.asset_it_support.entity.borrow.BorrowRequest;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;

import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BorrowService {


    private final BorrowRepository borrowRepository;

    private final EquipmentRepository equipmentRepository;

    private final BorrowStatusRepository borrowStatusRepository;

    private final EmployeeRepository employeeRepository;


    private final EquipmentStatusRepository equipmentStatusRepository;


    private final RoleService roleService;
    private final BorrowStatusService borrowStatusService;


    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // รันทุกวันตอนเที่ยงคืน
    public void updateOverdueBorrowStatus() {
        borrowRepository.updateOverdueStatus();
    }


    public List<BorrowView> findAllBorrowed() {
        try {
            List<BorrowView> borrowViews = borrowRepository.findAllBorrow();
            return borrowViews;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<BorrowView> searchBorrowEquipment(@Param("keyword") String keyword) {
        List<BorrowView> borrowViews = borrowRepository.searchBorrowEquipment(keyword);
        return borrowViews;
    }


//    public List<BorrowView> filterStatusAndRole(@RequestParam Integer borrowStatusId,
//                                                @RequestParam Integer roleId) {
//        if (borrowStatusId != null && roleId == null) {
//            return borrowStatusService.findAll(borrowStatusId);
//        }
//        if (borrowStatusId == null && roleId != null) {
//            return roleService.findAll(roleId);
//        }
//        if (borrowStatusId != null && roleId != null) {
//            return borrowRepository.findByDynamicFilter(borrowStatusId, roleId);
//        }
//        return borrowRepository.findAllBorrow();
//    }


    @Transactional
    public BorrowResponse createBorrow(BorrowRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow();

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
