package net.axisdata.restaurant.controller;

import net.axisdata.restaurant.dto.SeatDTO;

import net.axisdata.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/seat")
    @ResponseBody
    public void request(@RequestBody SeatDTO seatDTO) {
        return restaurantService.useSeat(seatDTO) + "";
    }
}
