package com.example.hotelsystem.controller;

import com.example.hotelsystem.entity.Hotel;
import com.example.hotelsystem.entity.Room;
import com.example.hotelsystem.payload.RoomDTO;
import com.example.hotelsystem.repository.HotelRepository;
import com.example.hotelsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomContoroller {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    Page<Room> getRooms(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return roomRepository.findAll(pageable);
    }

    @GetMapping("/byHotelId/{id}")
    Page<Room> getRoomsByHotelId(@PathVariable Integer id, @RequestParam int page) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Pageable pageable = PageRequest.of(page, 5);
            Page<Room> roomsByHotelId = roomRepository.findAllByHotel_HotelId(id, pageable);
            return roomsByHotelId;
        }
        return null;
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDTO roomDTO) {
        Room room = new Room();
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDTO.getHotel_id());
        if(optionalHotel.isPresent()){
            room.setHotel(optionalHotel.get());
        }
        else
            return "Hotel not found";

        if (roomRepository.existsByRoomNumberAndHotel_HotelId(roomDTO.getRoomNumber(), roomDTO.getHotel_id())) {
            return "Room already exists";
        }
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setFloor(roomDTO.getFloor());
        room.setSize(roomDTO.getSize());
        roomRepository.save(room);
        return "Room added";
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id,@RequestBody RoomDTO roomDTO){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            Room editRoom = optionalRoom.get();
            Optional<Hotel> optionalHotel = hotelRepository.findById(roomDTO.getHotel_id());
            if(!optionalHotel.isPresent()){
                return "Hotel not found";
            }
            Hotel hotel = optionalHotel.get();
            if(roomRepository.existsByRoomIdIsNotAndRoomNumberAndHotel_HotelId(id,roomDTO.getRoomNumber(),roomDTO.getHotel_id())){
                return "Room already exists";
            }
            editRoom.setHotel(hotel);
            editRoom.setRoomNumber(roomDTO.getRoomNumber());
            editRoom.setSize(roomDTO.getSize());
            editRoom.setFloor(roomDTO.getFloor());
            roomRepository.save(editRoom);
            return "Room edited";
        }
        return "Room not found";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            roomRepository.delete(optionalRoom.get());
            return "Room deleted";
        }
        return "Room not found";
    }
}
