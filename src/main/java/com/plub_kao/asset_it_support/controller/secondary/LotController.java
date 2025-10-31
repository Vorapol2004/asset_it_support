package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.lot.Lot;
import com.plub_kao.asset_it_support.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lot")
public class LotController {

    @Autowired
    private LotService lotService;


    @PostMapping("/add")
    public ResponseEntity<Lot> addLotWithEquipments(@RequestBody Lot lot) {
        Lot newLot = lotService.addLot(lot);
        return new ResponseEntity<>(newLot, HttpStatus.CREATED);
    }

}
