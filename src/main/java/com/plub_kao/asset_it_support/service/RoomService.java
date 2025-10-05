package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.room.Room;
import com.plub_kao.asset_it_support.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> getAllRole() {
        return roomRepository.findAll();
    }


}
