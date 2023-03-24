package net.axisdata.restaurant.service;

import net.axisdata.restaurant.model.Restaurant;
import net.axisdata.restaurant.model.Table;
import net.axisdata.restaurant.model.TableStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
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

        logger.info("{} enters the take table function, request {}.", name, count);
        while (arriveStamp + waitTime > System.currentTimeMillis()) {
            try {
                if (takeTable(count, time, name)) return true;

                long currentWaitTime = waitTime - (System.currentTimeMillis() - arriveStamp);

                synchronized (restaurant.getMutexes()[count - 1]) {
                    restaurant.getMutexes()[count - 1].wait(currentWaitTime);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        logger.info("{} went away.", name);
        return false;
    }

    private boolean takeTable(int count, int time, String name) {
        lock.lock();
        for (int i = count; i <= restaurant.getBiggestTable(); i++) {
            TableStatus tableStatus = restaurant.getTables().get(i);
            if (tableStatus == null) continue;

            List<Table> freeTables = tableStatus.getFreeList();
            if (freeTables == null || freeTables.isEmpty()) continue;

            Table table = freeTables.get(0);

            restaurant.occupyTable(time, table, name);

            freeTables.remove(table);
            tableStatus.getOccupiedList().add(table);

            lock.unlock();
            return true;
        }
        lock.unlock();
        logger.info("{} will have to wait.", name);
        return false;
    }
}