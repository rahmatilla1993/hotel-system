package com.example.hotelsystem.repository;

import com.example.hotelsystem.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Page<Room> findAllByHotel_HotelId(Integer hotel_hotelId, Pageable pageable);

    boolean existsByRoomNumberAndHotel_HotelId(String roomNumber, Integer hotel_hotelId);

    boolean existsByRoomIdIsNotAndRoomNumberAndHotel_HotelId(Integer roomId, String roomNumber, Integer hotel_hotelId);
}
