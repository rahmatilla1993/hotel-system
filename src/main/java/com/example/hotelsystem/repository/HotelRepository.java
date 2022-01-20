package com.example.hotelsystem.repository;

import com.example.hotelsystem.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {

    boolean existsByHotelName(String hotelName);
    boolean existsByHotelIdIsNotAndHotelName(Integer hotelId, String hotelName);
}
