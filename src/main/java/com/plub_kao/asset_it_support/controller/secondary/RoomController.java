package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.department.DepartmentDto;
import com.plub_kao.asset_it_support.entity.floor.FloorView;
import com.plub_kao.asset_it_support.entity.room.RoomDto;
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

    @PostMapping("/create")
    public RoomDto create(@RequestBody RoomDto dto) {
        return roomService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(
            @PathVariable Integer id,
            @RequestBody RoomDto request
    ) {
        RoomDto updated = roomService.update(id, request);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        roomService.deleteRoom(id);
    }


}
