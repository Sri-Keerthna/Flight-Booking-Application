package com.spiralforge.easefly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

import com.spiralforge.easefly.dto.FlightListResponseDto;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.exception.FlightNotFoundException;
import com.spiralforge.easefly.repository.FlightRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FlightServiceTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlightServiceTest.class);

	@InjectMocks
	FlightServiceImpl flightService;

	@Mock
	FlightRepository flightRepository;
	
	List<FlightListResponseDto> listResponseDto = new ArrayList<>();
	FlightListResponseDto FlightListResponseDto =new FlightListResponseDto();
	Flight flight = new Flight();
	List<Flight> flightList = new ArrayList<>();
	String sourceName="Chennai";
	String destinationName = "Bangalore";
	LocalDate date=LocalDate.of(2020, 02, 03);
	Integer noOfTraveller=2;
	
	@Before
	public void setUp() {
		flight.setFlightId(1);
		flight.setSourceName("Chennai");
		flight.setDestinationName("Bangalore");
		flight.setFlightDate(LocalDate.of(2020, 02, 05));
		flight.setPrice(20000D);
		flight.setStartTime(LocalTime.of(10, 00, 00));
		flight.setEndTime(LocalTime.of(12, 00, 00));
		flightList.add(flight);
		
		BeanUtils.copyProperties(flight, FlightListResponseDto);
		listResponseDto.add(FlightListResponseDto);
	}
	
	@Test
	public void testSearchFlightPositive() throws FlightNotFoundException {
		Mockito.when(flightRepository.findBySourceandDestinationName(sourceName, destinationName, date,
				noOfTraveller)).thenReturn(flightList);
		logger.info("Got the list of flights");
		List<FlightListResponseDto> result = flightService.searchFlight(sourceName, destinationName, date, noOfTraveller);
		assertEquals(1, result.size());
	}
	
	@Test(expected = FlightNotFoundException.class )
	public void testSearchFlightNegative() throws FlightNotFoundException {
		List<Flight> flightLists = new ArrayList<>();
		Mockito.when(flightRepository.findBySourceandDestinationName(sourceName, destinationName, date,
				noOfTraveller)).thenReturn(flightLists);
		logger.info("Got the list of flights");
		flightService.searchFlight(sourceName, destinationName, date, noOfTraveller);
	}

}
