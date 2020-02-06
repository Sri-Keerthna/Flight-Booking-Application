package com.spiralforge.easefly.exception;

/**
 * @author Sri Keerthna.
 * @since 2020-02-03.
 */
public class BookingCannotCancelledException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * if cancellation date exceeds traveldate then it will throw an error.
	 * @param message is sent to traveller.
	 */
	public BookingCannotCancelledException(String message) {
		super(message);
	}
}
