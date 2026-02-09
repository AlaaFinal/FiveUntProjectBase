package com.example.AlaaFinal.model;

public class Table {
    public String id;
    public String tableName;
    public int tableNumber;
    public int seatsNum;
    public int customersNum;
    public boolean isReserved;

    public Table(String tableName, int tableNumber, int seatsNum, int customersNum, boolean isReserved) {
        this.id = "t_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
        this.tableName = tableName;
        this.tableNumber = tableNumber;
        this.seatsNum = seatsNum;
        this.customersNum = customersNum;
        this.isReserved = isReserved;
    }

    public Table() {
        this.id = "t_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getSeatsNum() {
        return seatsNum;
    }

    public void setSeatsNum(int seatsNum) {
        this.seatsNum = seatsNum;
    }

    public int getCustomersNum() {
        return customersNum;
    }

    public void setCustomersNum(int customersNum) {
        this.customersNum = customersNum;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        this.isReserved = reserved;
    }
}
