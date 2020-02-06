package com.spiralforge.easefly.utils;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.spiralforge.easefly.dto.BookingRequestDto;
import com.spiralforge.easefly.exception.BookingDataInvalidException;

/**
 * 
 * @author Sujal The booking validator is used to validate the booking
 *         information
 *
 */
@Component("bookingValidator")
public class BookingValidatorImpl implements BookingValidator<BookingRequestDto> {

	@Override
	public Boolean validate(BookingRequestDto bookingRequestDto) throws BookingDataInvalidException {
		
		if(Objects.isNull(bookingRequestDto.getPaymentType())) {
			throw new BookingDataInvalidException(ApiConstant.INVALID_PAYMENT_TYPE);
		}else if(bookingRequestDto.getTravelDate().isBefore(LocalDate.now())) {
			throw new BookingDataInvalidException(ApiConstant.INVALID_TRAVEL_DATE);
		}
		else if(bookingRequestDto.getTravellerList().size()<1) {
			throw new BookingDataInvalidException(ApiConstant.NO_TRAVELLER_FOUND);
		}
		return true;
	}

}
