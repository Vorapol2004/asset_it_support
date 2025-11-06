package com.plub_kao.asset_it_support.entity.borrow;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.borrowStatus.BorrowStatus;
import com.plub_kao.asset_it_support.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "borrow")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "reference_doc")
    private String referenceDoc;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "borrow_status_id", nullable = false)
    private BorrowStatus borrowStatus;

    @Column(name = "approver_name", nullable = false, length = 100)
    private String approverName;

    @OneToMany(mappedBy = "borrow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowEquipment> borrowEquipments = new ArrayList<>();


}