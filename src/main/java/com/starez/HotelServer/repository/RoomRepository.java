package com.starez.HotelServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starez.HotelServer.Entity.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByAvailable(boolean available, PageRequest pageRequest);
}
