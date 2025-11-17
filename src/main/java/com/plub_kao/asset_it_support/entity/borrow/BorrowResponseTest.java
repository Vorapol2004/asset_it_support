package com.plub_kao.asset_it_support.entity.borrow;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BorrowResponseTest {

    private Integer id;
    private LocalDate borrowDate;
    private String referenceDoc;
    private PersonInfo employee;
    private PersonInfo approver;

    private List<BorrowEquipment> equipments;

    @Getter
    @Setter
    public static class PersonInfo {
        private Integer id;
        private String name;
    }

    @Getter
    @Setter
    public static class BorrowEquipment {

        private Integer id;
        private String equipmentName;
        private String serialNumber;
        private String licenseKey;

    }

    @Getter
    @Setter
    public static class CategoryInfo {
        private Integer id;
        private String name;
    }


}
