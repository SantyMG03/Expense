package com.expensemanager.model;

public class User {
    private String name;
    private String psw;

    /**
     * Constructor de un usuario con contraseña, se usara para inicializar un nuevo usuario
     * @param name nombre del usuario / nickname
     * @param psw contraseña del usuario
     */
    public User(String name, String psw) {
        this.name = name;
        this.psw = psw;
    }

    public User(String name) {
        this.name = name;
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
        return "User {" + ", name=" + name + '\'' +'}';
    }
}
