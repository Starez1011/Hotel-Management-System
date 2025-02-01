package com.starez.HotelServer.Services.admin.rooms;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.starez.HotelServer.Entity.Room;
import com.starez.HotelServer.dto.RoomDto;
import com.starez.HotelServer.dto.RoomsResponseDto;
import com.starez.HotelServer.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {
    
    private final RoomRepository roomRepository;

    public boolean postRoom(RoomDto roomDto) {
        try{
            Room room = new Room();
            room.setName(roomDto.getName());
            room.setType(roomDto.getType());
            room.setPrice(roomDto.getPrice());
            room.setAvailable(true);
            roomRepository.save(room);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public RoomsResponseDto getAllRooms(int pageNumber){
        PageRequest pageable = PageRequest.of(pageNumber, 2);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        RoomsResponseDto roomsResponseDto = new RoomsResponseDto();
        roomsResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomsResponseDto.setTotalPages(roomPage.getTotalPages());
        roomsResponseDto.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));
        return roomsResponseDto;
    }

    public RoomDto getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()){
            return room.get().getRoomDto();
        }else{
            throw new EntityNotFoundException("Room not present.");
        }
    }
    public boolean updateRoom(Long id, RoomDto roomDto) {
        Optional<Room> room1 = roomRepository.findById(id);
        if(room1.isPresent()){
            Room room = room1.get();
            room.setName(roomDto.getName());
            room.setType(roomDto.getType());
            room.setPrice(roomDto.getPrice());
            room.setAvailable(roomDto.isAvailable());
            roomRepository.save(room);
            return true;
        }else{
            return false;
        }
    }
    public boolean deleteRoom(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()){
            roomRepository.delete(room.get());
            return true;
        }else{
            return false;
        }
    }
}
