package com.example.tw1tt3rServer.repository;

import com.example.tw1tt3rServer.repository.entity.Room;
import com.example.tw1tt3rServer.repository.entity.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    public Room findByName(String name);
    public boolean existsByName(String name);
    public List<Room> findAllByRoomType(RoomType roomType);
}
