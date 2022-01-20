package com.example.hotelsystem.payload;

import com.example.hotelsystem.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private String roomNumber;
    private Integer size;
    private Integer floor;
    private Integer hotel_id;
}
