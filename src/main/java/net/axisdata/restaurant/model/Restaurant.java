package net.axisdata.restaurant.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private static Restaurant restaurant;
    private List<Table> tables;

    private int biggestTable;

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
}
