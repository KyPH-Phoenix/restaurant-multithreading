package net.axisdata.restaurant.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Table {
    private int seats;
    private boolean occupied = false;
    private long waitTime = 0;
    private final Object object;

    public Table(int nSeats, Object object) {
        this.object = object;
        this.seats = nSeats;
    }

    public int getSeats() {
        return this.seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void ocuppy(long time) {
        this.occupied = true;
        this.waitTime = time;

        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final Runnable checkTableTask = () -> {
            try {
                Thread.sleep(this.waitTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.occupied = false;
            this.waitTime = 0;
            final Logger logger = LoggerFactory.getLogger(Table.class);
            logger.info("table cleared");

            synchronized (this.object) {
                this.object.notifyAll();
            }
        };

        executor.execute(checkTableTask);
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

    public Object getObject() {
        return object;
    }
}
