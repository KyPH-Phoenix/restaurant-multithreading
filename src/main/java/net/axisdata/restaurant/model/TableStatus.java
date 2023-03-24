package net.axisdata.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class TableStatus {
    private List<Table> occupiedList = new ArrayList<>();
    private List<Table> freeList = new ArrayList<>();

    public List<Table> getOccupiedList() {
        return occupiedList;
    }

    public List<Table> getFreeList() {
        return freeList;
    }
}
