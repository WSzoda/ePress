package com.example.projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User {
    private int privligies;

    public User() {
        privligies = 0;
    }

    public String logIn(String username, String password) throws FileNotFoundException {
        File file = new File("src/main/resources/database/users.txt");
        Scanner scanner = new Scanner(file);
        String userLine;
        while (scanner.hasNext()) {
            //user data should have format username;password;privilegeNumber
            userLine = scanner.nextLine();
            String[] userInfoArray = userLine.split(";");

            if (userInfoArray[0].equals(username) && userInfoArray[1].equals(password)) {
                privligies = Integer.parseInt(userInfoArray[2]);
                return choosePageDependingOnPrivligies(privligies);
            }
        }
        return null;
    }

    private String choosePageDependingOnPrivligies(int privligies) {
        if (privligies == 1) {
            return "programowy-page.fxml";
        }
        if (privligies == 2) {
            return "handlowy-page.fxml";
        }
        return null;
    }


}
