package com.example;

@Component
public class UserService {
    @Autowired
    private DatabaseService databaseService;

    public void saveUser(String username){
        System.out.println("UserService is saving user...");
        databaseService.save(username);
    }
}

