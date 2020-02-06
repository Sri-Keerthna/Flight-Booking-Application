package com.spiralforge.easefly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.easefly.entity.BookingCancel;

@Repository
public interface BookingCancelRepository extends JpaRepository<BookingCancel, Integer>{

}
