package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.floor.FloorView;
import com.plub_kao.asset_it_support.entity.room.RoomView;
import com.plub_kao.asset_it_support.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {


    private final RoomService roomService;

    @GetMapping("/filter")
    public ResponseEntity<List<RoomView>> filterFloor(@RequestParam Integer floorId) {
        List<RoomView> RoomView = roomService.filterRoom(floorId);
        return ResponseEntity.ok(RoomView);
    }


}
