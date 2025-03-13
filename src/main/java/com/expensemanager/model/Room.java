package com.expensemanager.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id;
    private String name;
    private List<User> users;

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name='" + name + '\'' + ", users=" + users + '}';
    }
}
