package com.starez.HotelServer.Services.admin.reservation;

import com.starez.HotelServer.dto.ReservationResponseDto;

public interface ReservationService {
    ReservationResponseDto getAllReservations(int pageNumber) ;
    boolean changeReservationStatus(Long id, String status) ;
}
