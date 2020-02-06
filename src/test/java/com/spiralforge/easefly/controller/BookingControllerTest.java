package com.spiralforge.easefly.controller;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.spiralforge.easefly.dto.BookingRequestDto;
import com.spiralforge.easefly.dto.BookingResponseDto;
import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.entity.Traveller;
import com.spiralforge.easefly.exception.BookingDataInvalidException;
import com.spiralforge.easefly.exception.InvalidFlightIdException;
import com.spiralforge.easefly.service.BookingService;
import com.spiralforge.easefly.utils.StringConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
@WebMvcTest(BookingController.class)
public class BookingControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlightControllerTest.class);

	@InjectMocks
	private BookingController bookingController;

	@Mock
	private BookingService bookingService;

	Booking booking =new Booking();
	Traveller traveller= new Traveller();
	Flight flight= new Flight();
	BookingRequestDto bookingRequesteDto = new BookingRequestDto();
	BookingResponseDto bookingResponseDto = new BookingResponseDto();
	List<Traveller> travellerList= new ArrayList<Traveller>();
	Integer bookingId = 1;

	private MockMvc mockMvc;

	
	@Before
	public void setUp() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

		bookingRequesteDto.setPaymentType("PhonePE");
		bookingRequesteDto.setTravelDate(LocalDate.now());
		
		traveller.setTravellerId(1);
		
		flight.setFlightId(1);
		flight.setFlightName("Vistara");
		flight.setFlightDate(LocalDate.now());
		flight.setPrice(3000D);
		flight.setSourceName("chennai");
		flight.setDestinationName("kolkata");
		flight.setTotalSeat(20);
		
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
	}

	@Test
	public void testBookingAPIPositive() throws Exception {

		bookingId=1;
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/flights/bookings/"+bookingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();

		assertNotNull(mvcResult);

		Mockito.verify(bookingService).getBookingDetails(bookingId);
	}
	
	@Test
	public void testBookingAPINegative() throws Exception {

		bookingId=2;
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/flights/bookings").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

		assertNotNull(mvcResult);
	}
	
	@Test
	public void testBookingPositive() throws BookingDataInvalidException, InvalidFlightIdException {
		logger.info("Entered into testFilterFlightPositive method in controller");
		Mockito.when(bookingService.booking(1, bookingRequesteDto)).thenReturn(booking);
		Integer result = bookingService.booking(1,bookingRequesteDto).getNoOfSeats();
		Assert.assertEquals(2, result.intValue());
	}
}
