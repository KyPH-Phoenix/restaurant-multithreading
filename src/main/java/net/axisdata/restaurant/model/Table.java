package net.axisdata.restaurant.model;

public class Table {
    private int seats;
    private boolean occupied = false;
    private long waitTime = 0;

    public Table(int nSeats) {
        this.seats = nSeats;
    }

    public int getSeats() {
        return seats;
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
