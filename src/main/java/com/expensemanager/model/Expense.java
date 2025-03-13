package com.expensemanager.model;

public class Expense {
    private int id;
    private double amount;
    private User payer;
    private Room room;

    public Expense(int id, double amount, User user, Room room) {
        this.id = id;
        this.amount = amount;
        this.payer = user;
        this.room = room;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }

    public User getPayer() {
        return payer;
    }
    public void setPayer(User payer) {
        this.payer = payer;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Expense{" + "id=" + id + ", amount=" + amount + ", payer=" + payer + ", room=" + room + '}';

    }
}
