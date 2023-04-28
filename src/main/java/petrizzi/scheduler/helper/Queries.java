package petrizzi.scheduler.helper;

import javafx.fxml.FXML;
import petrizzi.scheduler.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Queries {

    public static void createCustomer(String customerName, String address, String postalCode, String phoneNumber, int divisionID) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divisionID);
        ps.executeUpdate();
    }

    public static void createAppointment(String title, String type, String description, String location, LocalDateTime start, LocalDateTime end, int customerID, int userID, String contactName) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Type, Description, Location, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, type);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, Queries.selectContactID(contactName));
        ps.executeUpdate();
    }

    public static void updateCustomer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);
        ps.executeUpdate();
    }

    public static void updateAppointment(int ID, String title, String type, String description, String location, LocalDateTime start, LocalDateTime end, int customerID, int userID, String contactName) throws SQLException {
        String sql = "UPDATE appointments SET Title=?, Type=?, Description=?, Location=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, type);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, Queries.selectContactID(contactName));
        ps.setInt(10, ID);
        ps.executeUpdate();
    }

    @FXML
    public static ArrayList<Appointment> selectAppointments() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Appointment> appointments = new ArrayList<>();
        while (rs.next()){
            int ID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String type = rs.getString("Type");
            String desc = rs.getString("Description");
            String location = rs.getString("Location");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int custID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointment appointment = new Appointment(ID, title, desc, location, type, start, end, custID, userID, contactID);
            appointments.add(appointment);
        }
        return appointments;
    }


    public static ArrayList<String> selectRegions(int ID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> entityList = new ArrayList<>();
        while (rs.next()){
            String division = rs.getString("Division");
            entityList.add(division);
        }
        return entityList;
    }

    public static ArrayList<String> selectContactNames() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> contactList = new ArrayList<>();

        while (rs.next()) {
            String contact = rs.getString("Contact_Name");
            contactList.add(contact);
        }
        return contactList;
    }

    public static ArrayList<Integer> selectCustomerIDs() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> custIDList = new ArrayList<>();

        while (rs.next()) {
            int ID = rs.getInt("Customer_ID");
            custIDList.add(ID);
        }
        return custIDList;
    }

    public static ArrayList<Integer> selectUserIDs() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> userIDList = new ArrayList<>();

        while (rs.next()) {
            int ID = rs.getInt("User_ID");
            userIDList.add(ID);
        }
        return userIDList;
    }

    public static int selectRegionID(String regionName) throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, regionName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("Division_ID");
        } else return 0;
    }

    public static int selectContactID(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("Contact_ID");
        } else return 0;
    }

    public static String selectContactName(int contactID) throws SQLException {
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("Contact_Name");
        } else return null;
    }

    public static String selectRegionName(int divisionID) throws SQLException {
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("Division");
        } else return sql;
    }

    public static String selectCountry(int divisionID) throws SQLException {
        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String country;
            int cID = rs.getInt("Country_ID");
            if (cID == 1) country = "United States";
            else if (cID == 2) country = "United Kingdom";
            else country = "Canada";
            return country;
        }
        return sql;
    }

    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        String sql2 = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
        ps.setInt(1, customerId);
        ps.executeUpdate();
        ps2.setInt(1, customerId);
        ps2.executeUpdate();
    }

    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.executeUpdate();
    }

    public static boolean checkAppointmentOverlap (int customerID, LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            LocalDateTime existingStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime existingEnd = rs.getTimestamp("End").toLocalDateTime();
            if ((start.isAfter(existingStart.minusMinutes(1)) && start.isBefore(existingEnd.plusMinutes(1))) ||
                    (end.isAfter(existingStart.minusMinutes(1)) && end.isBefore(existingEnd.plusMinutes(1))) ||
                    (start.isBefore(existingStart.plusMinutes(1)) && end.isAfter(existingEnd.minusMinutes(1)))) {
                return true; //Appointment has an overlap.
            }
        }
        return false; //Appointment has no overlap.
    }

    public static boolean checkAppointmentOverlap(int customerID, LocalDateTime start, LocalDateTime end, int currentAppointmentID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND Appointment_ID != ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.setInt(2, currentAppointmentID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            LocalDateTime existingStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime existingEnd = rs.getTimestamp("End").toLocalDateTime();
            if ((start.isAfter(existingStart.minusMinutes(1)) && start.isBefore(existingEnd.plusMinutes(1))) ||
                    (end.isAfter(existingStart.minusMinutes(1)) && end.isBefore(existingEnd.plusMinutes(1))) ||
                    (start.isBefore(existingStart.plusMinutes(1)) && end.isAfter(existingEnd.minusMinutes(1)))) {
                return true; //Appointment has an overlap.
            }
        }
        return false; //Appointment has no overlap.
    }


    public static String selectRegion(int divisionID) throws SQLException {
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("Division");
        }else return null;

    }
}

