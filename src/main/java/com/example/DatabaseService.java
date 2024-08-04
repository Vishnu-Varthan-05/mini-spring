package com.example;

@Component
public class DatabaseService {
    public void save(String data){
        System.out.println("DatabaseService is saving: " + data);
    }
}
