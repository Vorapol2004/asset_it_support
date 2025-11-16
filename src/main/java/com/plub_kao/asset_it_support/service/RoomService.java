package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.building.Building;
import com.plub_kao.asset_it_support.entity.floor.Floor;
import com.plub_kao.asset_it_support.entity.floor.FloorDto;
import com.plub_kao.asset_it_support.entity.floor.FloorView;
import com.plub_kao.asset_it_support.entity.room.Room;
import com.plub_kao.asset_it_support.entity.room.RoomDto;
import com.plub_kao.asset_it_support.entity.room.RoomView;
import com.plub_kao.asset_it_support.repository.FloorRepository;
import com.plub_kao.asset_it_support.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final FloorRepository floorRepository;

    public List<RoomView> filterRoom(@RequestParam Integer Id) {
        try {
            return roomRepository.filterRoomByFloor(Id);
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }
    }

    public RoomDto create(RoomDto RoomDto) {
        Floor Floor = floorRepository.findById(RoomDto.getFloorId())
                .orElseThrow(() -> new RuntimeException("Department not found"));


        Room room = new Room();
        room.setRoomName(RoomDto.getRoomName());
        room.setFloor(Floor);

        Room savedRoom = roomRepository.save(room);
        RoomDto.setId(savedRoom.getId());

        return RoomDto;
    }

    public RoomDto update(Integer id, RoomDto request) {

        Room Room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("room not found"));
        Floor Floor = floorRepository.findById(request.getFloorId())
                .orElseThrow(() -> new RuntimeException("Floor not found"));

        Room.setRoomName(request.getRoomName());
        Room.setFloor(Floor);


        Room updated = roomRepository.save(Room);
        request.setId(updated.getId());
        return request;
    }

    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }


}
