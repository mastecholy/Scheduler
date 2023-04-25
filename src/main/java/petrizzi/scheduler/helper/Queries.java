package petrizzi.scheduler.helper;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import petrizzi.scheduler.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    };

    //public static void createAppt(String title, String description, String location, )

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
    };


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
    };

    public static ArrayList<String> selectContacts() throws SQLException {
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
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }





}

