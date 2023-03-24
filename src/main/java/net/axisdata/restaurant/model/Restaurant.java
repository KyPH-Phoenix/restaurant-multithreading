package net.axisdata.restaurant.model;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {
    private static final Logger logger = LoggerFactory.getLogger(Restaurant.class);
    private static Restaurant restaurant;
    private Int2ObjectMap<TableStatus> tables;
    private int biggestTable;
    private Object[] mutexes;
    private ExecutorService executor;


    private Restaurant() {

    }

    public static Restaurant getInstance() {
        if (restaurant != null) return restaurant;

        restaurant = new Restaurant();
        FileReader fileReader;

        try {
            fileReader = new FileReader("src/main/resources/tables.json");
            JSONParser parser = new JSONParser();

            JSONArray jsonTableArray = (JSONArray) parser.parse(fileReader);
            int biggest = 0;

            Int2ObjectMap<TableStatus> tableMap = new Int2ObjectOpenHashMap<>();

            int nTables = 0;

            for (Object o : jsonTableArray) {
                int nSeats = Integer.parseInt(o.toString());
                if (nSeats > biggest) biggest = nSeats;

                TableStatus tableStatus = tableMap.computeIfAbsent(nSeats, key -> new TableStatus());

                tableStatus.getFreeList().add(new Table(nSeats));

                nTables++;
            }

            Object[] mutexes = new Object[biggest];

            for (int i = 0; i < biggest; i++) {
                mutexes[i] = new Object();
            }

            restaurant.mutexes = mutexes;
            restaurant.executor = Executors.newFixedThreadPool(nTables);
            restaurant.biggestTable = biggest;
            restaurant.tables = tableMap;

            logger.info("Restaurant created succesfully.");

            return restaurant;
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Int2ObjectMap<TableStatus> getTables() {
        return tables;
    }

    public int getBiggestTable() {
        return biggestTable;
    }

    public Object[] getMutexes() {
        return mutexes;
    }

    public void occupyTable(int time, Table table, String name) {
        executor.execute(() -> {
            logger.info("{} seats table occupied for {}ms. ({})", table.getSeats(), time, name);

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.tables.get(table.getSeats()).getOccupiedList().remove(table);
            this.tables.get(table.getSeats()).getFreeList().add(table);
            logger.info("table cleared. ({} seats, {})", table.getSeats(), name);

            for (int i = table.getSeats() - 1; i >= 0; i--) {
                Object mutex = this.mutexes[i];
                synchronized (mutex) {
                    mutex.notifyAll();
                }
            }
        });
    }
}
