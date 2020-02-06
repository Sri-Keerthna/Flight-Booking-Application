package com.spiralforge.easefly.controller;

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

import com.spiralforge.easefly.dto.BookingCancelResponseDto;
import com.spiralforge.easefly.exception.BookingCannotCancelledException;
import com.spiralforge.easefly.exception.BookingIdNotFoundException;
import com.spiralforge.easefly.service.BookingCancelService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BookingCancelControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlightControllerTest.class);

	@InjectMocks
	BookingCancelController bookingCancelController;

	@Mock
	BookingCancelService bookingCancelService;

	BookingCancelResponseDto bookingCancelResponseDto = new BookingCancelResponseDto();
	Integer bookingId = 1;

	@Before
	public void setUp() {
		bookingCancelResponseDto.setMessage("Cancelled successfully");
		bookingCancelResponseDto.setStatusCode(200);
	}

	@Test
	public void testbookingCancellationPositive() throws BookingIdNotFoundException, BookingCannotCancelledException {
		logger.info("Entered into bookingCancellation method in controller");
		Mockito.when(bookingCancelService.bookingCancellation(bookingId)).thenReturn(bookingCancelResponseDto);
		Integer result = bookingCancelController.bookingCancellation(bookingId).getStatusCodeValue();
		Assert.assertEquals(200, result.intValue());
	}
}
