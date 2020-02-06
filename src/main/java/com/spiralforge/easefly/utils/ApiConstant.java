package com.spiralforge.easefly.utils;

/**
 * 
 * @author Sujal
 *
 */
public class ApiConstant {
	private ApiConstant() {
	}

	public static final String LOGIN_ERROR = "please enter valid username and password";
	public static final String LOGIN_SUCCESS = "you are successfully logged in";
	public static final String CREDIT_CARD_TYPE = "credit";

	public static final String SUCCESS = "SUCCESSFUL";
	public static final String FAILED = "FAILED";

	public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
	public static final String VALIDATION_FAILED = "VALIDATION FAILED";
	public static final String NO_ELEMENT_FOUND = "NO ELEMENT FOUND";
	public static final String CUSTOMER_NOT_FOUND = "Not a valid customer";

	public static final String GMAILID = "testmail2521@gmail.com";
	public static final String GMAIL_SUBJECT = "Flight Ticket Booking";
	public static final String TEXT_ONE = "You have booked the flight ticket successfully. Your booking id is ";
	public static final String HEXT_TWO = "Dear Customer,";
	public static final String TEXT_TWO = "Booking details:";
	public static final String TEXT_THREE = "THANK YOU FOR BOOKING TICKETS THROUGH EASE-FLY.";
	public static final String TEXT_FOUR = "NOTE: Please don't reply to this mail.";
	public static final String NEXT_LINE = "\n";
	public static final String WHITE_SPACE = "\t";

	public static final Integer SUCCESS_CODE = 200;
	public static final Integer FAILURE_CODE = 404;
	public static final Integer NO_CONTENT_CODE = 204;

	public static final String FLIGHTS_NOT_FOUND = "No flight available";

	public static final String NO_TRAVELLER_FOUND = "Provide at least 1 traveller";
	public static final String INVALID_TRAVEL_DATE = "Travel date should be after current date.";
	public static final String INVALID_PAYMENT_TYPE = "Payment type is not provided.";
	public static final String NO_FLIGHT_FOUND = "Kindly select a valid flight";

	public static final String BOOKINGID_NOT_FOUND = "Booking id not found";
	public static final String BOOKING_CANCELED_SUCCESS = "Booking cancelled successfull";
	public static final Integer STATUS_CODE = 200;
	public static final String BOOKING_CANNOT_CANCEL = "Date for cancellation got exceeded";

}
