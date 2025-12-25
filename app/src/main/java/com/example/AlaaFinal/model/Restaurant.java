package com.example.AlaaFinal.model;
import java.util.ArrayList;

public class Restaurant
{
    public String name;
    public ArrayList<Table> tables;

    public Restaurant(String name, ArrayList<Table> tables) {
        name = name;
        tables = tables;
    }

    public Restaurant() {
    }

    public String getName() {
        return name;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setName(String name) {
        name = name;
    }

    public void setTables(ArrayList<Table> tables) {
        tables = tables;
    }
}
