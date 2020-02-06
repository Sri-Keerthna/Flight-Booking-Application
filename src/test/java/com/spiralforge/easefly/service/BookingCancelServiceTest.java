package com.spiralforge.easefly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spiralforge.easefly.controller.FlightControllerTest;
import com.spiralforge.easefly.dto.BookingCancelResponseDto;
import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.exception.BookingCannotCancelledException;
import com.spiralforge.easefly.exception.BookingIdNotFoundException;
import com.spiralforge.easefly.repository.BookingCancelRepository;
import com.spiralforge.easefly.repository.BookingRepository;
import com.spiralforge.easefly.repository.FlightRepository;
import com.spiralforge.easefly.utils.StringConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BookingCancelServiceTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlightControllerTest.class);

	@InjectMocks
	BookingCancelServiceImpl bookingCancelService;

	@Mock
	BookingRepository bookingRepository;

	@Mock
	FlightRepository flightRepository;

	@Mock
	BookingCancelRepository bookingCancelRepository;

	BookingCancelResponseDto bookingCancelResponseDto = new BookingCancelResponseDto();
	Integer bookingId = 1;
	Flight flight = new Flight();
	Booking booking = new Booking();

	@Before
	public void setUp() {
		flight.setFlightId(1);
		flight.setSourceName("Chennai");
		flight.setDestinationName("Bangalore");
		flight.setFlightDate(LocalDate.of(2020, 02, 10));
		flight.setPrice(20000D);
		flight.setStartTime(LocalTime.of(10, 00, 00));
		flight.setEndTime(LocalTime.of(12, 00, 00));
		flight.setTotalSeat(10);
		
		bookingCancelResponseDto.setMessage("Cancelled successfully");
		bookingCancelResponseDto.setStatusCode(200);

		booking.setBookingId(1);
		booking.setNoOfSeats(2);
		booking.setPaymentType("gpay");
		booking.setPrimaryEmailid("sri@gmail.com");
		booking.setStatus("booked");
		booking.setTravelDate(LocalDate.of(2020, 02, 10));
		booking.setTotalPrice(20000D);
		booking.setFlight(flight);
	}

	@Test(expected = BookingCannotCancelledException.class)
	public void testbookingCancellationNegative() throws BookingIdNotFoundException, BookingCannotCancelledException {
		booking.setTravelDate(LocalDate.of(2020, 02, 03));
		Mockito.when(bookingRepository.findByBookingIdAndStatus(bookingId, StringConstant.BOOKED))
				.thenReturn(Optional.of(booking));
		logger.error("Cancellation can't be done");
		bookingCancelService.bookingCancellation(bookingId);
	}

	@Test(expected = BookingIdNotFoundException.class)
	public void testbookingCancellationNegativeTest() throws BookingIdNotFoundException, BookingCannotCancelledException {
		Mockito.when(bookingRepository.findByBookingIdAndStatus(1, StringConstant.BOOKED))
				.thenReturn(Optional.ofNullable(booking));
		logger.error("Booking id not found");
		bookingCancelService.bookingCancellation(2);
	}
	
	@Test
	public void testbookingCancellationPositive() throws BookingIdNotFoundException, BookingCannotCancelledException {
		Mockito.when(bookingRepository.findByBookingIdAndStatus(bookingId, StringConstant.BOOKED))
				.thenReturn(Optional.of(booking));
		Mockito.when(flightRepository.findByFlightId(flight.getFlightId())).thenReturn(flight);
		logger.info("Cancellation is done");
		bookingCancelResponseDto= bookingCancelService.bookingCancellation(bookingId);
		assertEquals(200, bookingCancelResponseDto.getStatusCode());
	}
}
