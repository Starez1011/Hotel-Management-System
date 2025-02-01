package com.starez.HotelServer.Services.admin.rooms;

import com.starez.HotelServer.dto.RoomDto;
import com.starez.HotelServer.dto.RoomsResponseDto;

public interface RoomsService {
    
    boolean postRoom(RoomDto roomDto);
    RoomsResponseDto getAllRooms(int pageNumber);
    RoomDto getRoomById(Long id);

    boolean updateRoom(Long id, RoomDto roomDto) ;

    boolean deleteRoom(Long id) ;
}
