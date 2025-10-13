package com.plub_kao.asset_it_support.entity.equipment.view;

import java.time.LocalDate;

public interface EquipmentView {

    Integer getId();

    Integer getEquipmentTypeId();

    String getEquipmentTypeName();

    String getEquipmentName();

    String getBrand();

    String getModel();

    String getSerialNumber();

    String getLicenseKey();

    Integer getEquipmentStatusId();

    String getEquipmentStatusName();

    Integer getLotId();

    String getLotName();

    String getAcademicYear();

    String getReferenceDoc();

    String getDescription();

    LocalDate getPurchaseDate();

    LocalDate getExpireDate();

    Integer getLotTypeId();

    String getLotTypeName();

}
