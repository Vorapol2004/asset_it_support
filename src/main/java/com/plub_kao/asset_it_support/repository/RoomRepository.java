package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.room.Room;
import com.plub_kao.asset_it_support.entity.room.view.RoomView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {


    @Query(value = """
            SELECT
                `id`,
                `room_name`,
                `floor_id`,
                `department_id`
            FROM
                `room`
            
            """, nativeQuery = true)
    List<RoomView> getRooms();

}


