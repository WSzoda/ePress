package com.example.projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User {
    private String name;
    private int privligies;
    private IDepartment department;

    public User(){
        name = "";
        privligies = 0;
    }

    public void logIn(String username, String password) throws FileNotFoundException {
        File file = new File("src/main/resources/database/users.txt");
        Scanner scanner = new Scanner(file);
        String userLine;
        while (scanner.hasNext()){
            //user data should have format username;password;privilegeNumber;name
            userLine = scanner.nextLine();
            String[] userInfoArray = userLine.split(";");

            if(userInfoArray[0].equals(username)  && userInfoArray[1].equals(password)){
                name = userInfoArray[3];
                privligies = Integer.parseInt(userInfoArray[2]);
                System.out.println("UDalo sie zalogowac");
                break;
            }
        }
    }




}
