package petrizzi.scheduler.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract class that appends user login attempt information to a text file
 */
public abstract class LoginTracker {

    private static final String text_file = "login_activity.txt";


    /**
     * Method that appends user login attempt information to a text file with formatted dateTime, noting success or failure.
     * @param username username that login was used for login attempt.
     * @param access boolean that is true if user is granted access, false if access is denied.
     */
    public static void trackLogin(String username, boolean access) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String now = LocalDateTime.now().format(dateTimeFormatter);
        String message = String.format("%s -- User %s -- %s login.", now, username, access ? "Successful" : "Unsuccessful");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(text_file, true))){
            writer.write(message);
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Failed to track login");
            e.printStackTrace();
        }
    }
}
