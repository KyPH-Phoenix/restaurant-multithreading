package net.axisdata.restaurant.model;

public class RequestData {
    private final Object mutex = new Object();
    private final int count;

    public RequestData(int count) {
        this.count = count;
    }

    public Object getMutex() {
        return mutex;
    }

    public int getCount() {
        return count;
    }
}
