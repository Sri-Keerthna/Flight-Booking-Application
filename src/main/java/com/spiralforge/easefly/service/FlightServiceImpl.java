package com.spiralforge.easefly.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.easefly.dto.FlightListResponseDto;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.exception.FlightNotFoundException;
import com.spiralforge.easefly.repository.FlightRepository;
import com.spiralforge.easefly.utils.ApiConstant;

/**
 * @author Sri Keerthna.
 * @since 2020-02-03.
 *
 */
@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	FlightRepository flightRepository;

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

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
	@Override
	public List<FlightListResponseDto> searchFlight(String sourceName, String destinationName, LocalDate date,
			Integer noOfTraveller) throws FlightNotFoundException {
		List<FlightListResponseDto> flightListResponseDto = new ArrayList<>();
		List<Flight> flightList = flightRepository.findBySourceandDestinationName(sourceName, destinationName, date,
				noOfTraveller);
		if (flightList.isEmpty()) {
			logger.error("No flight is available");
			throw new FlightNotFoundException(ApiConstant.FLIGHTS_NOT_FOUND);
		} else {
			logger.info("Got the list of flights");
			flightList.forEach(list -> {
				FlightListResponseDto responseDto = new FlightListResponseDto();
				BeanUtils.copyProperties(list, responseDto);
				flightListResponseDto.add(responseDto);
			});
			return flightListResponseDto;
		}

	}

}
