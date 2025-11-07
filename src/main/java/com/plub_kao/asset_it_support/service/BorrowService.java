package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.controller.main.ReturnRequest;
import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.borrowStatus.BorrowStatus;
import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.BorrowResponse;
import com.plub_kao.asset_it_support.entity.borrow.BorrowRequest;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;

import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;

import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatus;
import com.plub_kao.asset_it_support.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
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
    private final BorrowEquipmentRepository borrowEquipmentRepository;


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


    public List<BorrowView> selectBorrowId(@RequestParam Integer borrowId) {
        try {
            List<BorrowView> borrowViews = borrowRepository.selectBorrow(borrowId);
            return borrowViews;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public List<BorrowView> filterStatusAndRole(@RequestParam Integer borrowStatusId,
                                                @RequestParam Integer roleId) {
        if (borrowStatusId != null && roleId == null) {
            return borrowStatusService.FilterBorrowStatus(borrowStatusId);
        }
        if (borrowStatusId == null && roleId != null) {
            return roleService.FilterBorrowStatus(roleId);
        }
        if (borrowStatusId != null && roleId != null) {
            return borrowRepository.findByDynamicFilter(borrowStatusId, roleId);
        }
        return borrowRepository.findAllBorrow();
    }


    @Transactional
    public BorrowResponse createBorrow(BorrowRequest request) {

        Set<Integer> unique = new HashSet<>(request.getEquipmentIds());
        if (unique.size() != request.getEquipmentIds().size()) {
            throw new IllegalArgumentException("มีอุปกรณ์ซ้ำในรายการ ไม่สามารถยืมซ้ำใน request เดียวกันได้");
        }

        List<Equipment> equipments = equipmentRepository.findAllById(request.getEquipmentIds());

        for (Equipment eq : equipments) {
            if (eq.getEquipmentStatus().getId() != 1) {
                throw new IllegalArgumentException("Equipment id " + eq.getId() + " is not available to borrow.");
            }
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        BorrowStatus borrowStatus = borrowStatusRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("BorrowStatus not found"));

        Borrow borrow = new Borrow();
        borrow.setEmployee(employee);
        borrow.setBorrowStatus(borrowStatus);
        borrow.setBorrowDate(request.getBorrowDate());
        borrow.setApproverName(request.getApproverName());
        borrow.setReferenceDoc(request.getReferenceDoc());

        List<BorrowEquipment> borrowEquipmentList = new ArrayList<>();
        for (Integer equipmentId : request.getEquipmentIds()) {


            Equipment equipment = equipmentRepository.findById(equipmentId)
                    .orElseThrow(() -> new IllegalArgumentException(" equipment " + equipmentId));


            boolean isBorrowingNow = borrowEquipmentRepository.existsByEquipmentIdAndReturnDateIsNull(equipmentId);

            if (isBorrowingNow) {
                throw new IllegalArgumentException("อุปกรณ์ ID " + equipmentId + " กำลังถูกยืมอยู่ ไม่สามารถยืมซ้ำได้");
            }

            equipment.setEquipmentStatus(equipmentStatusRepository.findById(2)
                    .orElseThrow(() -> new IllegalArgumentException(" equipment " + equipmentId)));
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
        response.setApproverName(savedBorrow.getApproverName());
        response.setEquipments(new ArrayList<>());

        List<BorrowEquipment> borrowEquipments = savedBorrow.getBorrowEquipments();
        for (BorrowEquipment borrowEquipment : borrowEquipments) {
            response.addEquipmentInfo(borrowEquipment.getEquipment());
        }

        return response;
    }


    public String returnEquipment(ReturnRequest request) {


        BorrowEquipment borrowEquipment = borrowEquipmentRepository.findById(request.getBorrowerEquipmentId()).orElseThrow();
        borrowEquipment.setReturnDate(request.getReturnDate());
        borrowEquipmentRepository.save(borrowEquipment);

        Equipment equipment = borrowEquipment.getEquipment();
        equipment.setEquipmentStatus(equipmentStatusRepository.findById(request.getStatusId()).orElseThrow());
        equipmentRepository.save(equipment);

        Borrow borrow = borrowEquipment.getBorrow();
        boolean allReturned = true;
        for (BorrowEquipment be : borrow.getBorrowEquipments()) {
            if (be.getReturnDate() == null) {
                allReturned = false;
                break;
            }
        }

        BorrowStatus borrowStatus = borrowStatusRepository.findById(allReturned ? 2 : 3).orElseThrow();
        borrow.setBorrowStatus(borrowStatus);
        borrowRepository.save(borrow);

        return "returned successfully";
    }
}
