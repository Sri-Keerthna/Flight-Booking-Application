package com.spiralforge.easefly.exception;

/**
 * @author Sri Keerthna.
 * @since 2020-02-03.
 */
public class BookingIdNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @author Sri Keerthna
	 * If the booking id is not found then it will throw an error
	 * @param message is thrown as booking id not found.
	 */
	public BookingIdNotFoundException(String message) {
		super(message);
	}
}
