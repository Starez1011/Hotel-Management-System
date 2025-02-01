package com.starez.HotelServer.Services.customer.booking;

import com.starez.HotelServer.dto.ReservationDto;
import com.starez.HotelServer.dto.ReservationResponseDto;

public interface BookingService {
    
    boolean postReservation(ReservationDto reservationDto) ;

    ReservationResponseDto getAllReservationByUserId(Long userId,int pageNumber);
}
