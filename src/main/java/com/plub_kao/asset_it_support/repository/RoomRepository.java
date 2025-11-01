package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {


}


