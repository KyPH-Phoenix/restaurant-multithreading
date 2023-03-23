package net.axisdata.restaurant.controller;

import net.axisdata.restaurant.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/seat")
    @ResponseBody
    public String request(@RequestParam String name, @RequestParam int time,
                          @RequestParam int count, @RequestParam int waitTime) {
        return restaurantService.useSeat(name, time, count, waitTime) + "";
    }
}
