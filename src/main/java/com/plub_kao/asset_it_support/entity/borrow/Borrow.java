package com.plub_kao.asset_it_support.entity.borrow;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.BorrowStatus;
import com.plub_kao.asset_it_support.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "borrow")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "reference_doc")
    private String referenceDoc;

    @OneToMany(mappedBy = "borrow")
    private List<BorrowEquipment> borrowEquipment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrow_status_id", nullable = false)
    private BorrowStatus borrowStatus;

    public Borrow(Employee employee, LocalDate borrowDate, List<BorrowEquipment> borrowEquipment, BorrowStatus borrowStatus, String referenceDoc) {
        this.employee = employee;
        this.borrowDate = borrowDate;
        this.borrowEquipment = borrowEquipment;
        this.borrowStatus = borrowStatus;
        this.referenceDoc = referenceDoc;
    }
}