package com.spiralforge.easefly.utils;

import com.spiralforge.easefly.exception.BookingDataInvalidException;

public interface BookingValidator<T> {
	
	Boolean validate(T t) throws BookingDataInvalidException;
}
