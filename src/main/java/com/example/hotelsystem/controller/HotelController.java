package com.example.hotelsystem.controller;

import com.example.hotelsystem.entity.Hotel;
import com.example.hotelsystem.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public Page<Hotel> getHotels(@RequestParam int page){
        Pageable pageable= PageRequest.of(page,5);
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);
        return hotelPage;
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        Hotel new_hotel=new Hotel();
        if(hotelRepository.existsByHotelName(hotel.getHotelName())){
            return "Hotel already exists";
        }
        new_hotel.setHotelName(hotel.getHotelName());
        hotelRepository.save(new_hotel);
        return "Hotel saved";
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id,@RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if(optionalHotel.isPresent()){
            Hotel editHotel = optionalHotel.get();
            if(hotelRepository.existsByHotelIdIsNotAndHotelName(id,hotel.getHotelName())){
                return "Hotel already exists";
            }
            editHotel.setHotelName(hotel.getHotelName());
            hotelRepository.save(editHotel);
            return "Hotel edited";
        }
        return "Hotel not found";
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if(optionalHotel.isPresent()){
            hotelRepository.delete(optionalHotel.get());
            return "Hotel deleted";
        }
        return "Hotel not found";
    }
}
