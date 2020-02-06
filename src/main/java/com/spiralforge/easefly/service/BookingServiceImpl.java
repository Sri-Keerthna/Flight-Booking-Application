package com.spiralforge.easefly.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.spiralforge.easefly.dto.BookingDetailsDto;
import com.spiralforge.easefly.dto.BookingRequestDto;
import com.spiralforge.easefly.dto.FlightRequestDto;
import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.entity.Traveller;
import com.spiralforge.easefly.exception.BookingDataInvalidException;
import com.spiralforge.easefly.exception.InvalidFlightIdException;
import com.spiralforge.easefly.payment.Payment;
import com.spiralforge.easefly.payment.PaymentFactory;
import com.spiralforge.easefly.repository.BookingRepository;
import com.spiralforge.easefly.repository.FlightRepository;
import com.spiralforge.easefly.repository.TravellerRepository;
import com.spiralforge.easefly.utils.ApiConstant;
import com.spiralforge.easefly.utils.BookingValidator;
import com.spiralforge.easefly.utils.StringConstant;

@Service
public class BookingServiceImpl implements BookingService {

	Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private TravellerRepository travellerRepository;

	@Autowired
	private PaymentFactory paymentFactory;

	@Autowired
	private BookingValidator<BookingRequestDto> bookingValidator;

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * @author Sujal This method is used to filter search data
	 * 
	 * @param flightRequestDto
	 * @param flightName
	 * @param shortBy
	 * @return list of flights
	 */
	@Override
	public List<Flight> filterFlight(FlightRequestDto flightRequestDto, String flightName, String sortBy) {
		logger.info("Inside filter flight method " + flightName);
		List<Flight> flights = null;
		if (sortBy.equalsIgnoreCase("DESC"))
			sortBy = "f.price DESC";
		else
			sortBy = "f.price ASC";

		if (Objects.isNull(flightName) || flightName.equalsIgnoreCase("") || flightName == "") {
			logger.info("inside flight name null");
			flights = flightRepository.filterFight(flightRequestDto.getSourceName(),
					flightRequestDto.getDestinationName(), flightRequestDto.getNoOfTraveller(),
					flightRequestDto.getDate(), sortBy);
		} else {
			flights = flightRepository.filterFight(flightRequestDto.getSourceName(),
					flightRequestDto.getDestinationName(), flightRequestDto.getNoOfTraveller(),
					flightRequestDto.getDate(), flightName, sortBy);
		}

		return flights;
	}

	/**
	 * 
	 * @author Sujal This Method is used to perform booking for the travelers
	 * 
	 * @param flightId
	 * @param bookingResponseDto
	 * @return
	 * @throws BookingDataInvalidException
	 * @throws InvalidFlightIdException
	 */
	@Transactional
	@Override
	public Booking booking(Integer flightId, BookingRequestDto bookingRequestDto)
			throws BookingDataInvalidException, InvalidFlightIdException {
		Optional<Flight> optionalFlight = flightRepository.findById(flightId);
		Booking booking = null;
		String primaryMailId = "";
		if (optionalFlight.isPresent() && bookingValidator.validate(bookingRequestDto)) {
			Optional<Traveller> optionalTraveller = bookingRequestDto.getTravellerList().stream()
					.filter(traveller -> traveller.getPrimaryTravellerFlag().equals(true)).findAny();
			if (optionalTraveller.isPresent())
				primaryMailId = optionalTraveller.get().getEmailId();

			Payment payment = paymentFactory.getPaymentMethod(bookingRequestDto.getPaymentType());
			if (payment.pay())
				booking = saveBookingData(optionalFlight.get(), bookingRequestDto, primaryMailId);
			if (!Objects.isNull(booking)) {
				for(Traveller travellerDto :bookingRequestDto.getTravellerList()) {
					Traveller traveller = new Traveller();
					BeanUtils.copyProperties(travellerDto, traveller);
					traveller.setBooking(booking);
					travellerRepository.save(traveller);
				}
			}
		} else {
			throw new InvalidFlightIdException(ApiConstant.NO_FLIGHT_FOUND);
		}

		if (!Objects.isNull(booking) && optionalFlight.isPresent()) {
			Flight flight = optionalFlight.get();
			Integer availableSeats = (flight.getTotalSeat() - bookingRequestDto.getTravellerList().size());
			logger.info("INSIDE FLIGHT SAVE "+availableSeats);

			flight.setTotalSeat(availableSeats);
			flightRepository.save(flight);

			sendMail(booking, bookingRequestDto, primaryMailId);
		}

		return booking;
	}

	/**
	 * @author Sujal This Method is used to save booking data
	 * @param optionalFlight
	 * @param bookingResponseDto
	 * @param primaryMailId
	 * @return
	 */
	@Transactional
	private Booking saveBookingData(Flight flight, BookingRequestDto bookingRequestDto, String primaryMailId) {
		Double totalPrice;
		Booking booking = new Booking();
		booking.setPrimaryEmailid(primaryMailId);
		booking.setNoOfSeats(bookingRequestDto.getTravellerList().size());
		totalPrice = (flight.getPrice() * bookingRequestDto.getTravellerList().size());
		booking.setTotalPrice(totalPrice);
		booking.setStatus(StringConstant.ACTIVE_STATUS);
		booking.setFlight(flight);
		booking.setBookingCreatedDate(LocalDateTime.now());
		booking.setBookingUpdatedDate(LocalDateTime.now());
		booking.setPaymentType(bookingRequestDto.getPaymentType());
		booking.setTravelDate(bookingRequestDto.getTravelDate());
		return bookingRepository.save(booking);
	}

	/**
	 * @author Sujal This Method is used to send email after the booking success
	 * @param booking
	 * @param bookingResponseDto
	 * @param primaryMailId
	 */
	@Async
	private void sendMail(Booking booking, BookingRequestDto bookingRequestDto, String primaryMailId) {
		try {
			StringBuilder text = new StringBuilder(ApiConstant.HEXT_TWO + ApiConstant.NEXT_LINE + ApiConstant.NEXT_LINE
					+ ApiConstant.TEXT_ONE + booking.getBookingId() + ApiConstant.NEXT_LINE + ApiConstant.NEXT_LINE);

			text.append(ApiConstant.TEXT_TWO + ApiConstant.NEXT_LINE + ApiConstant.NEXT_LINE + "FULL NAME"
					+ ApiConstant.WHITE_SPACE + "[ GENDER ]" + ApiConstant.NEXT_LINE);
			text.append("----------------------------------------------------------------------");
			for (Traveller traveller : bookingRequestDto.getTravellerList()) {
				text.append(ApiConstant.NEXT_LINE + traveller.getTravellerName() + " (" + traveller.getAge() + ")"
						+ ApiConstant.WHITE_SPACE + "[" + traveller.getGender() + "]");
			}
			text.append(ApiConstant.NEXT_LINE + ApiConstant.NEXT_LINE + ApiConstant.TEXT_THREE + ApiConstant.NEXT_LINE
					+ ApiConstant.NEXT_LINE + ApiConstant.TEXT_FOUR);

			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(primaryMailId);
			mail.setFrom(ApiConstant.GMAILID);
			mail.setSubject(ApiConstant.GMAIL_SUBJECT);
			mail.setText(text.toString());
			javaMailSender.send(mail);
		} catch (Exception exception) {
			logger.error("Unable to send the email");
		}
	}

	/**
	 * @author Sujal This Method is used to get the booking by the bookingid
	 * @param bookingid
	 * @return BookingDetails
	 */
	@Transactional
	@Override
	public BookingDetailsDto getBookingDetails(Integer bookingId) {
		BookingDetailsDto bookingDetailsDto = null;
		Optional<Booking> booking = bookingRepository.findByBookingIdAndStatus(bookingId, StringConstant.ACTIVE_STATUS);

		if (booking.isPresent()) {
			Optional<List<Traveller>> travellers = travellerRepository.findByBooking(booking.get());
			bookingDetailsDto = new BookingDetailsDto();
			bookingDetailsDto.setBooking(booking.get());
			if (travellers.isPresent())
				bookingDetailsDto.setTravellerList(travellers.get());
		}

		return bookingDetailsDto;
	}
}
