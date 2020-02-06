package com.spiralforge.easefly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.Traveller;

@Repository
public interface TravellerRepository extends JpaRepository<Traveller, Integer>{

	/**
	 * @author Sujal
	 * @param booking
	 * @return
	 */
	Optional<List<Traveller>> findByBooking(Booking booking);

}
