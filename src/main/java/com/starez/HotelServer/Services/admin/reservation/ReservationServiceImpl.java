package com.starez.HotelServer.Services.admin.reservation;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.starez.HotelServer.Entity.Reservation;
import com.starez.HotelServer.Entity.Room;
import com.starez.HotelServer.dto.ReservationResponseDto;
import com.starez.HotelServer.enums.ReservationStatus;
import com.starez.HotelServer.repository.ReservationRepository;
import com.starez.HotelServer.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    
    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;
    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public ReservationResponseDto getAllReservations(int pageNumber) {

        PageRequest pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto).collect(Collectors.toList()));
        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }


    public boolean changeReservationStatus(Long id, String status) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isPresent()) {

            Reservation existingReservation = reservation.get();

            if(Objects.equals(status, "Approve")){
                existingReservation.setStatus(ReservationStatus.APPROVED);
            }else{
                existingReservation.setStatus(ReservationStatus.REJECTED);
            }
            reservationRepository.save(existingReservation);

            Room existingRoom = existingReservation.getRoom();
            existingRoom.setAvailable(false);
            roomRepository.save(existingRoom);
            return true;
        } else {
            return false;
        }
    }
}
