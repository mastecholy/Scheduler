package petrizzi.scheduler.model;

import petrizzi.scheduler.helper.Queries;

import java.sql.SQLException;

/**
 * Class for creating customer objects that will be used to populate customer table view.
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;
    private String divisionLocation;

    /**
     * Constructor for creating customer object, queries for Location name using DivisionID.
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID used to set the divisionLocation String.
     * @throws SQLException
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.divisionLocation = Queries.selectRegion(divisionID);
    }

    /**
     * Getters and Setters
     */

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivisionLocation() {
        return divisionLocation;
    }

    public void setDivisionLocation(String divisionLocation) {
        this.divisionLocation = divisionLocation;
    }
}

