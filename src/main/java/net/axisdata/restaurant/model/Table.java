package net.axisdata.restaurant.model;

public class Table {
    private final int seats;

    public Table(int nSeats) {
        this.seats = nSeats;
    }

    public int getSeats() {
        return this.seats;
    }
}
