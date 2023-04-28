package petrizzi.scheduler.helper;

import javafx.fxml.FXML;
import petrizzi.scheduler.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Abstract class for executing queries in the database.
 */
public abstract class Queries {

    /**
     * Method that inserts a customer into the Customers table.
     * @param customerName Customer_Name column
     * @param address Address column
     * @param postalCode Postal_Code column
     * @param phoneNumber Phone column
     * @param divisionID Division_ID column
     * @throws SQLException
     */
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

    /**
     * Method that inserts and appointment into the appointments table.
     * @param title Title column
     * @param type Type column
     * @param description Description column
     * @param location Location column
     * @param start Start column
     * @param end End column
     * @param customerID Customer_ID column
     * @param userID User_ID column
     * @param contactName passed to selectContactID query
     * @throws SQLException
     */
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

    /**
     * Method for updating customer in edit customer form.
     * @param customerID Customer_ID column, used to select the customer to update
     * @param customerName Customer_Name column
     * @param address Address column
     * @param postalCode Postal_Code column
     * @param phoneNumber Phone column
     * @param divisionID Division_ID column
     * @throws SQLException
     */
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

    /**
     * Method for updating appointment in the edit appointment form.
     * @param ID ID column, used to select the appointment to update in the database.
     * @param title Title column
     * @param type Type column
     * @param description Description column
     * @param location Location column
     * @param start Start column
     * @param end End column
     * @param customerID Customer_ID column
     * @param userID User_ID column
     * @param contactName passed to selectContactID query.
     * @throws SQLException
     */
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

    /**
     * Method for selecting all appointments and putting them in an array list for tableView use.
     * @return returns ArrayList of all Appointments.
     * @throws SQLException
     */
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


    /**
     * Method for getting all first level divisions within selected country.
     * @param ID Country ID used to select divisions.
     * @return returns ArrayList of Strings containing division names used to populate comboBox.
     * @throws SQLException
     */
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

    /**
     * Method to get all contact names from contacts table in array list.
     * @return returns array list of Strings containing all contact names.
     * @throws SQLException
     */
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

    /**
     * Method for getting all customer IDs from customer table.
     * @return returns array list of integers representing all unique customer IDs.
     * @throws SQLException
     */
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

    /**
     * Method for selecting all UserIDs from users table.
     * @return returns array list of integers representing all unique customer IDs.
     * @throws SQLException
     */
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

    /**
     * Method for selecting a DivisionID from first_level_divisions table.
     * @param regionName string used to select the division from the table.
     * @return returns unique DivisionID as integer.
     * @throws SQLException
     */
    public static int selectRegionID(String regionName) throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, regionName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("Division_ID");
        } else return 0;
    }

    /**
     * Method for selecting specific contact ID from contacts table using contact name.
     * @param contactName String used to select specific contact from table.
     * @return returns string containing specific contact's name.
     * @throws SQLException
     */
    public static int selectContactID(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("Contact_ID");
        } else return 0;
    }

    /**
     * Method selecting contact name from contacts table using contactID.
     * @param contactID Integer uniquely identifying contact.
     * @return returns String containing contact name.
     * @throws SQLException
     */
    public static String selectContactName(int contactID) throws SQLException {
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("Contact_Name");
        } else return null;
    }

    /**
     * Method selecting Division (name) from first_level_divisions using primary key Division_ID.
     * @param divisionID Integer representing primary key Division_ID.
     * @return returns string containing the region name from column "Division"
     * @throws SQLException
     */
    public static String selectRegionName(int divisionID) throws SQLException {
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("Division");
        } else return sql;
    }

    /**
     * Method selecting countryID from first_level_divisions using DivisionID.
     * @param divisionID integer representing primary key Division_ID.
     * @return returns String representing country name.
     * @throws SQLException
     */
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

    /**
     * Method for removing customer from the database using primary key Customer_ID.
     * @param customerId integer representing primary key Customer_ID
     * @throws SQLException
     */
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

    /**
     * Method for removing customer from the database using primary key Appointment_ID.
     * @param appointmentID integer representing primary key Appointment_ID.
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.executeUpdate();
    }

    /**
     * Method for checking if the appointment to be created overlaps with an existing appointment for chosen customer.
     * @param customerID integer for selecting all the customer's appointments from appointments table.
     * @param start LocalDateTime for start of the appointment being created.
     * @param end LocalDateTime for end of the appointment being created.
     * @return returns true if appointment to be created has an overlap with existing appointment, false if not.
     * @throws SQLException
     */
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

    /**
     * Method for checking if appointment to be updated overlaps with an existing appointment for chosen customer.
     * @param customerID integer for selecting all the customer's appointments from the appointments table.
     * @param start LocalDateTime for start of the appointment being updated.
     * @param end LocalDatetime for end of the appointment being updated.
     * @param currentAppointmentID ID of appointment being edited, used to remove current appointment from overlap check.
     * @return returns true if appointment to be updated has an overlap with existing appointment, false if not.
     * @throws SQLException
     */
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


    /**
     * Method for selecting Division (Region Name) from first_level_divisions using primary key Division_ID.
     * @param divisionID integer representing primary key Division_ID.
     * @return returns string containing Division (Region Name)
     * @throws SQLException
     */
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

