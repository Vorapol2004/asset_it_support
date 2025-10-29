package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.BorrowResponse;
import com.plub_kao.asset_it_support.entity.borrow.NewBorrow;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;

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
