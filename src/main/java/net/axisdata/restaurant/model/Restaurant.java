package net.axisdata.restaurant.model;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {
    private static final Logger logger = LoggerFactory.getLogger(Restaurant.class);
    private static Restaurant restaurant;
    private List<Table> tables;
    private int biggestTable;
    private final Object object = new Object();

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
            List<Table> tableList = new ArrayList<>();
            int biggest = 0;

            for ( Object o : jsonTableArray) {
                int nSeats = Integer.parseInt(o.toString());
                if (nSeats > biggest) biggest = nSeats;
                tableList.add(new Table(nSeats));
            }

            restaurant.biggestTable = biggest;
            restaurant.tables = tableList;

            restaurant.initiateTableChecker();

            return restaurant;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        } catch (IOException e) {
            throw new RuntimeException("IOexception");
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse");
        }
    }

    public List<Table> getTables() {
        return tables;
    }

    public int getBiggestTable() {
        return biggestTable;
    }

    public Object getObject() {
        return object;
    }

    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private final Runnable checkTablesTask = () -> {
        long millis = 300;
        while (true) {
            tables.forEach(table -> {
                synchronized (object) {
                    if (!table.isOccupied()) object.notifyAll();
                    else {
                        table.reduceWaitTime(millis);
                        if (table.getWaitTime() <= 0) {
                            table.setWaitTime(0);
                            table.setOccupied(false);
                            logger.info("table cleared");
                            object.notifyAll();
                        }
                    }
                }
            });

            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private void initiateTableChecker() {
        executor.execute(checkTablesTask);
    }
}
