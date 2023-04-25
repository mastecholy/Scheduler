package petrizzi.scheduler.model;

import petrizzi.scheduler.helper.Queries;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    private int appointmentCustomerID;
    private int appointmentUserID;
    private int appointmentContactID;
    private String appointmentContactName;
    private String startString;
    private String endString;

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation,
                       String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmetEnd,
                       int appointmentCustomerID, int appointmentUserID, int appointmentContactID) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmetEnd;
        this.appointmentCustomerID = appointmentCustomerID;
        this.appointmentUserID = appointmentUserID;
        this.appointmentContactID = appointmentContactID;
        this.appointmentContactName = Queries.selectContactName(appointmentContactID);
        this.startString = appointmentStart.format(formatter);
        this.endString = appointmentStart.format(formatter);
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getAppointmentContactName() {
        return appointmentContactName;
    }

    public void setAppointmentContactName(String appointmentContactName) {
        this.appointmentContactName = appointmentContactName;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public int getAppointmentCustomerID() {
        return appointmentCustomerID;
    }

    public void setAppointmentCustomerID(int appointmentCustomerID) {
        this.appointmentCustomerID = appointmentCustomerID;
    }

    public int getAppointmentUserID() {
        return appointmentUserID;
    }

    public void setAppointmentUserID(int appointmentUserID) {
        this.appointmentUserID = appointmentUserID;
    }

    public int getAppointmentContactID() {
        return appointmentContactID;
    }

    public void setAppointmentContactID(int appointmentContactID) {
        this.appointmentContactID = appointmentContactID;
    }
}
