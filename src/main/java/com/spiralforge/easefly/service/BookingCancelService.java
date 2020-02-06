package com.spiralforge.easefly.service;

import com.spiralforge.easefly.dto.BookingCancelResponseDto;
import com.spiralforge.easefly.exception.BookingCannotCancelledException;
import com.spiralforge.easefly.exception.BookingIdNotFoundException;

/**
 * @author Sri Keerthna.
 * @since 2020-02-03.
 */
public interface BookingCancelService {

	public BookingCancelResponseDto bookingCancellation(Integer bookingId) throws BookingIdNotFoundException, BookingCannotCancelledException;
}
