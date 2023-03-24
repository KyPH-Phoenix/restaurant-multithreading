package net.axisdata.restaurant.service;

import net.axisdata.restaurant.model.Restaurant;
import net.axisdata.restaurant.model.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RestaurantService {
    static final Logger logger = LoggerFactory.getLogger(Restaurant.class);
    Restaurant restaurant = Restaurant.getInstance();
    Lock lock = new ReentrantLock();

    public boolean useSeat(String name, int time, int count, int waitTime) {
        if (count > restaurant.getBiggestTable()) {
            logger.info("Not enough space for {}", name);
            return false;
        }

        waitTime *= 1000;
        long arriveStamp = System.currentTimeMillis();

        while (arriveStamp + waitTime > System.currentTimeMillis()) {
            try {
                if (takeTable(count, time, name)) return true;

                long currentWaitTime = waitTime - (System.currentTimeMillis() - arriveStamp);

                synchronized (restaurant.getObject()) {
                    restaurant.getObject().wait(currentWaitTime);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        logger.info("{} went away.", name);
        return false;
    }

    private boolean takeTable(int count, int time, String name) {
        boolean occupied = false;

        logger.info("{} enters the take table function.", name);

        lock.lock();
        for (Table table : restaurant.getTables()) {
            if (!table.isOccupied() && table.getSeats() >= count) {
                //table.ocuppy(time);
                restaurant.manageTable(time, table);
                logger.info("{} occupies a table. ({} seats)", name, table.getSeats());
                occupied = true;
                break;
            }
        }
        lock.unlock();

        if (!occupied) logger.info("{} will have to wait.", name);
        return occupied;
    }
}