package com.expensemanager.model;

public class User {
    private int id;
    private String name;
    private String psw;

    /**
     * Constructor de un usuario con contraseña, se usara para inicializar un nuevo usuario
     * @param id identificador del usuario
     * @param name nombre del usuario / nickname
     * @param psw contraseña del usuario
     */
    public User(int id, String name, String psw) {
        this.id = id;
        this.name = name;
        this.psw = psw;
    }

    public User (int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getPsw() {
        return this.psw;
    }
    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public String toString() {
        return "User {" + "id=" + id + ", name=" + name + '\'' +'}';
    }
}
