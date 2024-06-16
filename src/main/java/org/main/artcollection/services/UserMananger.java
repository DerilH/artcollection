package org.main.artcollection.services;

import org.main.artcollection.models.User;
import org.main.artcollection.security.SecurityUtils;

import java.io.*;

public class UserMananger {
    public static User currentUser;
    private static final String FILE_NAME = "./users.data";

    public static void addUser(User user) throws IOException {
        new File(FILE_NAME).createNewFile();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(user.toString());
            writer.newLine();
        }
    }

    public static void authenticate(String username, String password) throws IOException {
        new File(FILE_NAME).createNewFile();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        while ((line = reader.readLine()) != null) {
            User user = User.fromString(line);
            if (user.getUsername().equals(username) && SecurityUtils.verifyPassword(password, user.getPasswordHash())) {
                reader.close();
                currentUser = user;
                return;
            }
        }
        reader.close();
    }

    public static boolean userExists(String username) throws IOException {
        new File(FILE_NAME).createNewFile();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        while ((line = reader.readLine()) != null) {
            User user = User.fromString(line);
            if (user.getUsername().equals(username)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }

    public static boolean registerUser(String name, String password)
    {
        try {
            String hashedPassword = SecurityUtils.hashPassword(password);
            User user = new User(name, hashedPassword);
            UserMananger.addUser(user);
            return true;
        } catch (IOException e) {
        }
        return false;
    }
}
