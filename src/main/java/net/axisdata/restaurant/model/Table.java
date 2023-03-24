package net.axisdata.restaurant.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Table {
    private final int seats;
    private boolean occupied = false;
    private long waitTime = 0;
    private final Logger logger = LoggerFactory.getLogger(Restaurant.class);

    public Table(int nSeats) {
        this.seats = nSeats;
    }

    public int getSeats() {
        return this.seats;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void ocuppy(long time) {
        this.occupied = true;
        this.waitTime = time;

        logger.info("{} seats table occupied for {}ms.", this.seats, time);
    }

    public void free() {
        this.occupied = false;
        this.waitTime = 0;

        logger.info("table cleared. ({} seats)", this.seats);
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public void reduceWaitTime(long waitTime) {
        this.waitTime -= waitTime;
    }
}
