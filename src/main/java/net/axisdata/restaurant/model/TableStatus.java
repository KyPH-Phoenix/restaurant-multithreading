package net.axisdata.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class TableStatus {
    private final List<Integer> occupiedList = new ArrayList<>();
    private final List<Integer> freeList = new ArrayList<>();

    public List<Integer> getOccupiedList() {
        return occupiedList;
    }

    public List<Integer> getFreeList() {
        return freeList;
    }
}
