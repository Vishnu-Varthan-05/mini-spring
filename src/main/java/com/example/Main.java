package com.example;

public class Main {
    public static void main(String[] args) {
        MiniApplication app = new MiniApplication();
        app.start(UserService.class, DatabaseService.class);

        UserService us = app.getBean(UserService.class);
        us.saveUser("Vishnu");
    }
}