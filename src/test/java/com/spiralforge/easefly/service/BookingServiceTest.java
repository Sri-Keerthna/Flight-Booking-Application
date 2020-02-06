package com.spiralforge.easefly.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.beans.BeanUtils;

import com.spiralforge.easefly.dto.BookingRequestDto;
import com.spiralforge.easefly.dto.BookingResponseDto;
import com.spiralforge.easefly.dto.FlightListResponseDto;
import com.spiralforge.easefly.dto.FlightRequestDto;
import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.entity.Traveller;
import com.spiralforge.easefly.exception.BookingDataInvalidException;
import com.spiralforge.easefly.exception.FlightNotFoundException;
import com.spiralforge.easefly.exception.InvalidFlightIdException;
import com.spiralforge.easefly.repository.BookingRepository;
import com.spiralforge.easefly.repository.FlightRepository;
import com.spiralforge.easefly.repository.TravellerRepository;
import com.spiralforge.easefly.utils.StringConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BookingServiceTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(BookingServiceTest.class);

	@InjectMocks
	private BookingServiceImpl bookingService;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private FlightRepository flightRepository;

	@Mock
	private TravellerRepository travellerRepository;

	List<FlightListResponseDto> listResponseDto = new ArrayList<>();
	FlightListResponseDto FlightListResponseDto = new FlightListResponseDto();
	Flight flight = new Flight();
	List<Flight> flightList = new ArrayList<>();
	String sourceName = "Chennai";
	String destinationName = "Bangalore";
	LocalDate date = LocalDate.of(2020, 02, 03);
	Integer noOfTraveller = 2;
	Booking booking = new Booking();
	Traveller traveller = new Traveller();
	Flight flight2 = new Flight();
	BookingRequestDto bookingRequesteDto = new BookingRequestDto();
	BookingResponseDto bookingResponseDto = new BookingResponseDto();
	FlightRequestDto flightRequestDto = new FlightRequestDto();
	List<Traveller> travellerList = new ArrayList<Traveller>();
	Integer bookingId = 1;
	String sortBy = "f.price DESC";

	@Before
	public void setUp() {
		flight.setFlightId(1);
		flight.setFlightName("Vistara");
		flight.setSourceName("Chennai");
		flight.setDestinationName("Bangalore");
		flight.setFlightDate(LocalDate.of(2020, 02, 05));
		flight.setPrice(20000D);
		flight.setStartTime(LocalTime.of(10, 00, 00));
		flight.setEndTime(LocalTime.of(12, 00, 00));
		flightList.add(flight);

		bookingRequesteDto.setPaymentType("PhonePE");
		bookingRequesteDto.setTravelDate(LocalDate.now());

		traveller.setTravellerId(1);

		flight2.setFlightId(1);
		flight2.setFlightName("Vistara");
		flight2.setFlightDate(LocalDate.now());
		flight2.setPrice(3000D);
		flight2.setSourceName("chennai");
		flight2.setDestinationName("kolkata");
		flight2.setTotalSeat(20);
		flightList.add(flight2);

		booking.setBookingId(1);
		booking.setNoOfSeats(2);
		booking.setPaymentType("PhonePE");
		booking.setStatus(StringConstant.ACTIVE_STATUS);
		booking.setTotalPrice(30000D);
		booking.setPrimaryEmailid("sujal@gmail.com");
		booking.setTravelDate(LocalDate.now());
		booking.setBookingCreatedDate(LocalDateTime.now());
		booking.setBookingUpdatedDate(LocalDateTime.now());
		booking.setFlight(flight);

		traveller.setAge(20);
		traveller.setGender("male");
		traveller.setPrimaryTravellerFlag(true);
		travellerList.add(traveller);
		bookingRequesteDto.setTravellerList(travellerList);

		bookingResponseDto.setMessage("Booking successfully");
		bookingResponseDto.setStatusCode(200);

		BeanUtils.copyProperties(flight, FlightListResponseDto);
		listResponseDto.add(FlightListResponseDto);

		flightRequestDto.setDate(LocalDate.now());
		flightRequestDto.setDestinationName("chennai");
		flightRequestDto.setSourceName("kolkata");
		flightRequestDto.setNoOfTraveller(2);
	}

	@Test
	public void testFilterFirstPositive() throws FlightNotFoundException {
		Mockito.when(flightRepository.filterFight(flightRequestDto.getSourceName(),
				flightRequestDto.getDestinationName(), flightRequestDto.getNoOfTraveller(), flightRequestDto.getDate(),sortBy))
				.thenReturn(flightList);
		logger.info("Got the list of flights");
		List<Flight> result = flightRepository.filterFight(flightRequestDto.getSourceName(),
				flightRequestDto.getDestinationName(), flightRequestDto.getNoOfTraveller(), flightRequestDto.getDate(),sortBy);
		assertEquals(2, result.size());
	}
	
	@Test
	public void testFilterSecondPositive() throws FlightNotFoundException {
		String flightName="Vistara";
		Mockito.when(flightRepository.filterFight(flightRequestDto.getSourceName(),
				flightRequestDto.getDestinationName(), flightRequestDto.getNoOfTraveller(), flightRequestDto.getDate(),flightName,sortBy))
				.thenReturn(flightList);
		logger.info("Got the list of flights");
		List<Flight> result = flightRepository.filterFight(flightRequestDto.getSourceName(),
				flightRequestDto.getDestinationName(), flightRequestDto.getNoOfTraveller(), flightRequestDto.getDate(),flightName,sortBy);
		assertEquals(2, result.size());
	}
	
	@Test
	public void testBookingPositive() throws  BookingDataInvalidException, InvalidFlightIdException {
		Mockito.when(flightRepository.findById(1))
				.thenReturn(Optional.of(flight));
		Mockito.when(bookingRepository.save(booking)).thenReturn(booking);
		Mockito.when(travellerRepository.save(traveller)).thenReturn(traveller);
		Mockito.when(flightRepository.save(flight)).thenReturn(flight);
		
		assertEquals(1, booking.getBookingId());
	}

}
