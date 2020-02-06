package com.spiralforge.easefly.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.easefly.entity.Booking;

/**
 * @author Sri Keerthna.
 * @since 2020-02-03.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-03. It will check with database whether this bookingId and
	 *        status is in booked.
	 * @param bookingId given to traveller while booking ticket
	 * @param status    It should be in booked status
	 * @return give the details of that booking.
	 */
	Optional<Booking> findByBookingIdAndStatus(Integer bookingId, String status);

}
