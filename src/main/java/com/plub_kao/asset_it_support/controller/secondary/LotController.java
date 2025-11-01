package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.lot.Lot;
import com.plub_kao.asset_it_support.entity.lot.LotRequest;
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
    public ResponseEntity<Lot> addLot(@RequestBody LotRequest request) {
        try {
            Lot savedLot = lotService.addLot(request);
            return new ResponseEntity<>(savedLot, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
