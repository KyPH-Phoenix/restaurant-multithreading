package net.axisdata.restaurant.model;

public class Table {
    private int seats;
    private boolean occupied = false;

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
}
