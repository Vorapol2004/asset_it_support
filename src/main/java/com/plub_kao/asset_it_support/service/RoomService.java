package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.Floor;
import com.plub_kao.asset_it_support.entity.room.Room;
import com.plub_kao.asset_it_support.repository.FloorRepository;
import com.plub_kao.asset_it_support.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    FloorRepository floorRepository;

    public List<Room> getAllRole() {
        return roomRepository.findAll();
    }

    public Room newRoom(Room room) {
        return roomRepository.save(room);
    }


    //moveRoomToNewBuilding
    public Room moveRoomToFloor(Integer roomId, Integer floorId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("Room not found"));
        Floor floor = floorRepository.findById(floorId).orElseThrow(
                () -> new IllegalArgumentException("Floor not found"));
        room.setFloor(floor);
        return roomRepository.save(room);
    }

}
