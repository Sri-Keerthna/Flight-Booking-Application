package com.spiralforge.easefly.exception;

public class BookingDataInvalidException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * if there is no flight available on that date or on that route and in another
	 * case if the passengers are more than total seats in flight it will throw this
	 * error.
	 * @param message no flight available message will be thrown.
	 */
	public BookingDataInvalidException(String message) {
		super(message);
	}

}
