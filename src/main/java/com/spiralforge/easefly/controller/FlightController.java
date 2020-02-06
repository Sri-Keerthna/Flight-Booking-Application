package com.spiralforge.easefly.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.easefly.dto.FlightListResponseDto;
import com.spiralforge.easefly.exception.FlightNotFoundException;
import com.spiralforge.easefly.service.FlightService;

/**
 * This will have the searchFlights.
 * 
 * @author Sri Keerthna.
 * @since 2020-02-03.
 */
@RestController
@RequestMapping("/flights")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class FlightController {

	/**
	 * This will inject the methods in the employeeService interface.
	 */
	@Autowired
	FlightService flightService;

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-03. In this method flights are searched based on source,
	 *        destination, date and number of travellers.
	 * @param sourceName      given by traveller.
	 * @param destinationName given by traveller.
	 * @param date            given by traveller.
	 * @param noOfTraveller   given by traveller.
	 * @return List of flights available on those parameters passed by traveller.
	 * @throws FlightNotFoundException if there is no flight available on that date
	 *                                 or on that route it will throw this error.
	 */
	@GetMapping
	public ResponseEntity<List<FlightListResponseDto>> searchFlight(@Valid @RequestParam String sourceName,
			@RequestParam String destinationName,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam Integer noOfTraveller) throws FlightNotFoundException {
		logger.info("Entered into searchFlight method in controller");
		return new ResponseEntity<>(flightService.searchFlight(sourceName, destinationName, date, noOfTraveller),
				HttpStatus.OK);
	}

}
