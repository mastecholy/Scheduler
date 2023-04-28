package petrizzi.scheduler.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginTracker {

    private static final String text_file = "login_activity.txt";


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
