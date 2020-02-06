package com.spiralforge.easefly.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.easefly.dto.BookingDetailsDto;
import com.spiralforge.easefly.dto.BookingRequestDto;
import com.spiralforge.easefly.dto.BookingResponseDto;
import com.spiralforge.easefly.dto.FlightRequestDto;
import com.spiralforge.easefly.dto.FlightResponseDto;
import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.exception.BookingDataInvalidException;
import com.spiralforge.easefly.exception.InvalidFlightIdException;
import com.spiralforge.easefly.service.BookingService;
import com.spiralforge.easefly.utils.ApiConstant;

/**
 * This controller is used to manage the booking of flight
 * 
 * @since 2020-02-03
 * @author Sujal
 */
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/flights")
public class BookingController {

	Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	/**
	 * @author Sujal
	 *
	 *         Method is used to filter and sort the flight result based on flight
	 *         name and price respectively. This filter and sorting will be
	 *         performed on the top of existing search.
	 *
	 * @param flightRequestDto have the existing search filters
	 * @param flightName       new search filter
	 * @param shortBy          is used to sort by the price
	 * @return list of flights after applying the filters and sort.
	 */
	@PostMapping()
	public ResponseEntity<FlightResponseDto> filterFlight(@RequestBody FlightRequestDto flightRequestDto,
			@RequestParam("flightName") String flightName, @RequestParam("sortBy") String sortBy) {

		FlightResponseDto flightResponseDto = new FlightResponseDto();
		List<Flight> flights = bookingService.filterFlight(flightRequestDto, flightName, sortBy);

		if (Objects.isNull(flights) || flights.isEmpty()) {
			flightResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			flightResponseDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(flightResponseDto, HttpStatus.NO_CONTENT);
		} else {
			flightResponseDto.setFlightList(flights);
			flightResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			flightResponseDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(flightResponseDto, HttpStatus.OK);

		}
	}

	/**
	 * @author Sujal Method is used to booked the flight ticket for the travelers.
	 * @param flightId
	 * @param bookingRequestDto
	 * @return
	 * @throws BookingDataInvalidException
	 * @throws InvalidFlightIdException 
	 */
	@PostMapping("/{flightId}/bookings")
	public ResponseEntity<BookingResponseDto> booking(@PathVariable("flightId") Integer flightId,
			@RequestBody BookingRequestDto bookingRequestDto) throws BookingDataInvalidException, InvalidFlightIdException {

		BookingResponseDto bookingResponseDto = new BookingResponseDto();
		Booking booking = bookingService.booking(flightId, bookingRequestDto);

		if (Objects.isNull(booking)) {
			bookingResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			bookingResponseDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(bookingResponseDto, HttpStatus.NO_CONTENT);
		} else {
			bookingResponseDto.setBookingId(booking.getBookingId());
			bookingResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			bookingResponseDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(bookingResponseDto, HttpStatus.OK);

		}
	}
	
	/**
	 * @author Sujal Method is used to view the booked flight ticket for the travelers.
	 * @param flightId
	 * @param bookingRequestDto
	 * @return booking
	 */
	@GetMapping("/bookings/{bookingId}")
	public ResponseEntity<BookingDetailsDto> getBooking(@PathVariable("bookingId") Integer bookingId){

		BookingDetailsDto bookingDetailsDto= bookingService.getBookingDetails(bookingId);

		if (Objects.isNull(bookingDetailsDto)) {
			bookingDetailsDto = new BookingDetailsDto();
			bookingDetailsDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			bookingDetailsDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(bookingDetailsDto, HttpStatus.NO_CONTENT);
		} else {
			bookingDetailsDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			bookingDetailsDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(bookingDetailsDto, HttpStatus.OK);

		}
	}

}
