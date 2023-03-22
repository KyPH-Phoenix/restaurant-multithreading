package net.axisdata.restaurant.dto;

public class SeatDTO {
    private long time;
    private int count;
    private int waitTime;
    private String name;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time + System.currentTimeMillis();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SeatDTO{" +
                "time=" + time +
                ", count=" + count +
                ", waitTime=" + waitTime +
                ", name='" + name + '\'' +
                '}';
    }
}
