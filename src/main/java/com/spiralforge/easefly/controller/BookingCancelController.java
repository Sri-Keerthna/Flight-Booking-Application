package com.spiralforge.easefly.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.easefly.dto.BookingCancelResponseDto;
import com.spiralforge.easefly.exception.BookingCannotCancelledException;
import com.spiralforge.easefly.exception.BookingIdNotFoundException;
import com.spiralforge.easefly.service.BookingCancelService;

/**
 * This will have the cancel booked flights.
 * @author Sri Keerthna.
 * @since 2020-02-03.
 */
@RestController
@RequestMapping("/bookingCancel")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class BookingCancelController {

	/**
	 * This will inject the methods in the employeeService interface.
	 */
	@Autowired
	BookingCancelService bookingCancelService;

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(BookingCancelController.class);

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-03. In this method if traveller wants to cancel ticket then
	 *        traveller will give the booking id and it can be cancelled.
	 * @param bookingId given to traveller while booking ticket.
	 * @return message will be sent as cancelled.
	 * @throws BookingIdNotFoundException      if booking id is not there it will
	 *                                         throw an error.
	 * @throws BookingCannotCancelledException if cancellation date exceeds
	 *                                         traveldate then it will throw an
	 *                                         error.
	 */
	@PostMapping
	public ResponseEntity<BookingCancelResponseDto> bookingCancellation(@Valid @RequestParam Integer bookingId)
			throws BookingIdNotFoundException, BookingCannotCancelledException {
		logger.info("Entered into bookingCancellation method in controller");
		return new ResponseEntity<>(bookingCancelService.bookingCancellation(bookingId), HttpStatus.OK);
	}
}
