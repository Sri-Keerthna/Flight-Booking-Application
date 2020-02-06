package com.spiralforge.easefly.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.easefly.dto.BookingCancelResponseDto;
import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.BookingCancel;
import com.spiralforge.easefly.entity.Flight;
import com.spiralforge.easefly.exception.BookingCannotCancelledException;
import com.spiralforge.easefly.exception.BookingIdNotFoundException;
import com.spiralforge.easefly.repository.BookingCancelRepository;
import com.spiralforge.easefly.repository.BookingRepository;
import com.spiralforge.easefly.repository.FlightRepository;
import com.spiralforge.easefly.utils.ApiConstant;
import com.spiralforge.easefly.utils.StringConstant;

/**
 * @author Sri Keerthna.
 * @since 2020-02-03.
 */
@Service
public class BookingCancelServiceImpl implements BookingCancelService {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(BookingCancelServiceImpl.class);

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	BookingCancelRepository bookingCancelRepository;

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
	@Transactional
	public BookingCancelResponseDto bookingCancellation(Integer bookingId)
			throws BookingIdNotFoundException, BookingCannotCancelledException {
		Optional<Booking> booking = bookingRepository.findByBookingIdAndStatus(bookingId, StringConstant.BOOKED);
		if (!booking.isPresent()) {
			logger.error("Booking id not found");
			throw new BookingIdNotFoundException(ApiConstant.BOOKINGID_NOT_FOUND);
		} else {
			LocalDate currentDate = LocalDate.now();
			if (!booking.get().getTravelDate().isAfter(currentDate)) {
				logger.error("Cancellation can't be done");
				throw new BookingCannotCancelledException(ApiConstant.BOOKING_CANNOT_CANCEL);
			} else {
				BookingCancel bookingCancel = new BookingCancel();
				Integer noOfSeat = booking.get().getNoOfSeats();
				Integer flightId = booking.get().getFlight().getFlightId();
				booking.get().setStatus(StringConstant.BOOKING_CANCEL);
				Flight flight = flightRepository.findByFlightId(flightId);
				Integer totalSeat = flight.getTotalSeat() + noOfSeat;
				flight.setTotalSeat(totalSeat);
				bookingCancel.setBooking(booking.get());
				bookingCancel.setStatus(StringConstant.BOOKING_CANCEL);
				flightRepository.save(flight);
				bookingCancelRepository.save(bookingCancel);
				BookingCancelResponseDto bookingCancelResponseDto = new BookingCancelResponseDto();
				bookingCancelResponseDto.setMessage(ApiConstant.BOOKING_CANCELED_SUCCESS);
				bookingCancelResponseDto.setStatusCode(ApiConstant.STATUS_CODE);
				logger.info("Cancellation is done");
				return bookingCancelResponseDto;
			}
		}
	}

}
