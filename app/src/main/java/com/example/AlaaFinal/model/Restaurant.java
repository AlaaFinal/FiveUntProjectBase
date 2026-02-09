package com.example.AlaaFinal.model;
import java.util.ArrayList;

public class Restaurant
{
    public String name;
    public ArrayList<Table> tables;

    public Restaurant(String name, ArrayList<Table> tables) {
        this.name = name;
        this.tables = tables;
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
        this.name = name;
    }

    public void setTables(ArrayList<Table> tables) {
        tables = tables;
    }
}
