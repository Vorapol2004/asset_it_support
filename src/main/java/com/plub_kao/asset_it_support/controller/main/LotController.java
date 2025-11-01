package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.dto.request.LotCreateRequest;
import com.plub_kao.asset_it_support.dto.response.LotResponse;
import com.plub_kao.asset_it_support.entity.lot.LotType;
import com.plub_kao.asset_it_support.repository.LotTypeRepository;
import com.plub_kao.asset_it_support.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lot")
@CrossOrigin(origins = "http://localhost:3000")
public class LotController {

    @Autowired
    private LotService lotService;


//    @PostMapping("/add")
//    public ResponseEntity<Lot> addLotWithEquipments(@RequestBody Lot lot) {
//        Lot newLot = lotService.addLot(lot);
//        return new ResponseEntity<>(newLot, HttpStatus.CREATED);
//    }


    //Plub

    @Autowired
    private LotTypeRepository lotTypeRepository;


    // ✅ ดึง LotType ทั้งหมด (เพิ่มเข้าใน LotController ได้เลย)
    @GetMapping("/all")
    public ResponseEntity<List<LotType>> getAllLotTypes() {
        List<LotType> lotTypes = lotTypeRepository.findAll();
        if (lotTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(lotTypes);
    }


    @PostMapping("/create") // ✅ path ตรงกับที่ Next.js ยิง
    public ResponseEntity<LotResponse> createLotWithEquipments(@RequestBody LotCreateRequest request) {
        LotResponse createdLot = lotService.createLotWithEquipments(request);
        return new ResponseEntity<>(createdLot, HttpStatus.CREATED);
    }

}
