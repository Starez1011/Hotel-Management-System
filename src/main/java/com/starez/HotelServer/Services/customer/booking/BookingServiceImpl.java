package com.starez.HotelServer.Services.customer.booking;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.starez.HotelServer.Entity.Reservation;
import com.starez.HotelServer.Entity.Room;
import com.starez.HotelServer.Entity.User;
import com.starez.HotelServer.dto.ReservationDto;
import com.starez.HotelServer.dto.ReservationResponseDto;
import com.starez.HotelServer.enums.ReservationStatus;
import com.starez.HotelServer.repository.ReservationRepository;
import com.starez.HotelServer.repository.RoomRepository;
import com.starez.HotelServer.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;
    
    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public boolean postReservation(ReservationDto reservationDto) {

        Optional<User> user = userRepository.findById(reservationDto.getUserId());
        Optional<Room> room = roomRepository.findById(reservationDto.getRoomId());

        if (room.isPresent() && user.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());
            reservation.setStatus(ReservationStatus.PENDING);
            reservation.setUser(user.get());
            reservation.setRoom(room.get());

            Long days = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
            reservation.setPrice(room.get().getPrice() * days);

            // System.out.println("Check-in Date: " + reservation.getCheckInDate());
            // System.out.println("Check-out Date: " + reservation.getCheckOutDate());
            // System.out.println("Days: " + days);


            reservationRepository.save(reservation);
            return true;
        } else {
            return false;
        }
    }

    public ReservationResponseDto getAllReservationByUserId(Long userId,int pageNumber) {
         PageRequest pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto).collect(Collectors.toList()));
        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }

}
