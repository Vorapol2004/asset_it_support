package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.controller.main.ReturnRequest;
import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.borrow.BorrowResponseTest;
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

import java.util.*;

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
    @Scheduled(cron = "/10 0 0 * * *") // รันทุกวันตอนเที่ยงคืน
    public void updateOverdueBorrowStatus() {

        System.out.println(">>> Running scheduled updateOverdueBorrowStatus()");
        borrowRepository.updateOverdueStatus();
        System.out.println(">>> Finished scheduled update");
    }


    public List<BorrowView> findAllBorrowed() {
        try {
            List<BorrowView> borrowViews = borrowRepository.findAllBorrow();
            return borrowViews;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<BorrowResponseTest> findAllBorrowedTest() {

        List<BorrowView> rows = borrowRepository.findAllBorrow();

        // ใช้ BorrowResponseTest แทน BorrowResponse
        Map<Integer, BorrowResponseTest> map = new LinkedHashMap<>();

        for (BorrowView row : rows) {

            // หา borrow id เดิม หรือสร้างใหม่
            BorrowResponseTest br = map.get(row.getId());
            if (br == null) {
                br = new BorrowResponseTest();
                br.setId(row.getId());
                br.setBorrowDate(row.getBorrowDate());
                br.setBorrowStatusId(row.getBorrowStatusId());
                br.setReferenceDoc(row.getReferenceDoc());
                br.setApproverName(row.getApproverName());
                br.setBorrowEquipmentCount(row.getBorrowEquipmentCount());
                // set employee
                BorrowResponseTest.EmployeeInfo emp = new BorrowResponseTest.EmployeeInfo();
                emp.setId(row.getEmployeeId());
                emp.setFirstName(row.getFirstName());
                emp.setLastName(row.getLastName());
                emp.setEmail(row.getEmail());
                emp.setPhone(row.getPhone());
                emp.setRoleName(row.getRoleName());
                emp.setDepartmentName(row.getDepartmentName());
                br.setEmployee(emp);

                // เตรียม list ของ BorrowEquipment
                br.setEquipments(new ArrayList<>());

                map.put(row.getId(), br);
            }

            // ถ้ามีอุปกรณ์ → เพิ่มลง list
            if (row.getEquipmentId() != null) {

                BorrowResponseTest.BorrowEquipment eq = new BorrowResponseTest.BorrowEquipment();
                eq.setId(row.getEquipmentId());
                eq.setBorrowEquipmentId(row.getBorrowEquipmentId());
                eq.setEquipmentName(row.getEquipmentName());
                eq.setSerialNumber(row.getSerialNumber());
                eq.setLicenseKey(row.getLicenseKey());
                eq.setBrand(row.getBrand());
                eq.setModel(row.getModel());
                eq.setEquipmentTypeName(row.getEquipmentTypeName());
                eq.setDueDate(row.getDueDate());
                eq.setReturnDate(row.getReturnDate());

                br.getEquipments().add(eq);
            }
        }

        return new ArrayList<>(map.values());
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
    public BorrowResponseTest createBorrow(BorrowRequest request) {

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

        return createBorrowResponseTest(borrowRepository.save(borrow));
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

    private BorrowResponseTest createBorrowResponseTest(Borrow savedBorrow) {

        BorrowResponseTest response = new BorrowResponseTest();
        response.setId(savedBorrow.getId());
        response.setBorrowDate(savedBorrow.getBorrowDate());
        response.setReferenceDoc(savedBorrow.getReferenceDoc());
        response.setApproverName(savedBorrow.getApproverName());
        response.setBorrowStatusId(savedBorrow.getBorrowStatus().getId());


        BorrowResponseTest.EmployeeInfo emp = new BorrowResponseTest.EmployeeInfo();
        emp.setId(savedBorrow.getEmployee().getId());
        emp.setFirstName(savedBorrow.getEmployee().getFirstName());
        emp.setLastName(savedBorrow.getEmployee().getLastName());
        emp.setPhone(savedBorrow.getEmployee().getPhone());
        emp.setEmail(savedBorrow.getEmployee().getEmail());
        emp.setRoleName(savedBorrow.getEmployee().getRole().getRoleName());
        emp.setDepartmentName(savedBorrow.getEmployee().getDepartment().getDepartmentName());

        response.setEmployee(emp);


        List<BorrowResponseTest.BorrowEquipment> eqList = new ArrayList<>();

        for (BorrowEquipment be : savedBorrow.getBorrowEquipments()) {

            Equipment eq = be.getEquipment();

            BorrowResponseTest.BorrowEquipment eqInfo = new BorrowResponseTest.BorrowEquipment();
            eqInfo.setId(eq.getId());
            eqInfo.setBorrowEquipmentId(be.getId());
            eqInfo.setEquipmentName(eq.getEquipmentName());
            eqInfo.setSerialNumber(eq.getSerialNumber());
            eqInfo.setLicenseKey(eq.getLicenseKey());


            eqInfo.setBrand(eq.getBrand());
            eqInfo.setModel(eq.getModel());
            eqInfo.setEquipmentTypeName(eq.getEquipmentType().getEquipmentTypeName());

            eqInfo.setDueDate(be.getDueDate());
            eqInfo.setReturnDate(be.getReturnDate());
            eqList.add(eqInfo);
        }
        response.setBorrowEquipmentCount(eqList.size());

        response.setEquipments(eqList);
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
