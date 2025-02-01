package com.starez.HotelServer.Services.customer.room;

import com.starez.HotelServer.dto.RoomsResponseDto;

public interface RoomService {

    RoomsResponseDto getAvailableRooms(int pageNumber);
}
