package net.axisdata.restaurant.service;

import net.axisdata.restaurant.dto.SeatDTO;
import net.axisdata.restaurant.model.Restaurant;

import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    Restaurant restaurant = Restaurant.getInstance();

    public boolean useSeat(SeatDTO seatDTO) {
        if (seatDTO.getCount() > restaurant.getBiggestTable()) return false;

        /* COMPROVATION */
        
        /* PUT THREAD TO SLEEP (IN QUEUE) IF THERE ARE NO TABLES AVAILABLE */

        /*  */

        return false;
    }
}
