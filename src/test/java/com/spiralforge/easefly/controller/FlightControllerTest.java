package com.spiralforge.easefly.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
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
import com.spiralforge.easefly.service.FlightService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FlightControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlightControllerTest.class);

	@InjectMocks
	FlightController flightController;

	@Mock
	FlightService flightService;
	
	List<FlightListResponseDto> listResponseDto = new ArrayList<FlightListResponseDto>();
	FlightListResponseDto FlightListResponseDto =new FlightListResponseDto();
	Flight flight = new Flight();
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
		
		BeanUtils.copyProperties(flight, FlightListResponseDto);
		listResponseDto.add(FlightListResponseDto);
		
	}
	
	@Test
	public void testSearchFlightPositive() throws FlightNotFoundException {
		logger.info("Entered into searchFlight method in controller");
		Mockito.when(flightService.searchFlight(sourceName, destinationName, date, noOfTraveller)).thenReturn(listResponseDto);
		Integer result = flightController.searchFlight(sourceName, destinationName, date, noOfTraveller).getStatusCodeValue();
		Assert.assertEquals(200, result.intValue());
	}
}
