package com.spiralforge.easefly.service;

import java.util.List;

import com.spiralforge.easefly.dto.BookingDetailsDto;
import com.spiralforge.easefly.dto.BookingRequestDto;
import com.spiralforge.easefly.dto.FlightRequestDto;
import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.exception.BookingDataInvalidException;
import com.spiralforge.easefly.exception.InvalidFlightIdException;

public interface BookingService {

	List<Flight> filterFlight(FlightRequestDto flightRequestDto, String flightName, String sortBy);

	Booking booking(Integer flightId, BookingRequestDto bookingRequestDto) throws BookingDataInvalidException, InvalidFlightIdException;

	BookingDetailsDto getBookingDetails(Integer bookingId);


}
