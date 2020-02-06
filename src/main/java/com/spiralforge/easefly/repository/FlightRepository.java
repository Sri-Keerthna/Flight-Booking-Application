package com.spiralforge.easefly.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spiralforge.easefly.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-03. Filtering the parameters given by traveller.
	 * @param sourceName      given by traveller.
	 * @param destinationName given by traveller.
	 * @param date            given by traveller.
	 * @param noOfTraveller   given by traveller.
	 * @return list of flights available based on given parameters.
	 */
	@Query("SELECT f FROM Flight f WHERE f.sourceName =:sourceName and f.destinationName =:destinationName and f.flightDate =:date and f.totalSeat >=:noOfTraveller")
	List<Flight> findBySourceandDestinationName(String sourceName, String destinationName, LocalDate date,
			Integer noOfTraveller);

	/**
	 * @author Sujal This method is used to filter search data
	 * 
	 * @param sourceName
	 * @param destinationName
	 * @param noOfTraveller
	 * @param date
	 * @param flightName
	 * @param sortBy
	 * @return
	 */
	@Query("SELECT f FROM Flight f WHERE f.sourceName like %:sourceName% and f.destinationName like %:destinationName% and f.flightDate =:date and f.totalSeat >=:noOfTraveller and f.flightName like %:flightName% order by :sortBy")
	List<Flight> filterFight(@Param("sourceName") String sourceName, @Param("destinationName") String destinationName,
			@Param("noOfTraveller") Integer noOfTraveller, @Param("date") LocalDate date,
			@Param("flightName") String flightName, @Param("sortBy") String sortBy);

	/**
	 * @author Sujal This method is used to sort the search data
	 * 
	 * @param sourceName
	 * @param destinationName
	 * @param noOfTraveller
	 * @param date
	 * @param sortBy
	 * @return
	 */
	@Query("SELECT f FROM Flight f WHERE f.sourceName like %:sourceName% and f.destinationName like %:destinationName% and f.flightDate =:date and f.totalSeat >=:noOfTraveller order by :sortBy")
	List<Flight> filterFight(@Param("sourceName") String sourceName, @Param("destinationName") String destinationName,
			@Param("noOfTraveller") Integer noOfTraveller, @Param("date") LocalDate date,
			@Param("sortBy") String sortBy);

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-03. It will check the flightId is present.
	 * @param flightId traveller booked with this flight
	 * @return give the details of that particular flighht.
	 */
	Flight findByFlightId(Integer flightId);

}
