package petrizzi.scheduler.model;

import java.security.Timestamp;
import java.time.LocalDateTime;

public class Customer {
    private int Customer_ID;
    private String Customer_Name;
    private  String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime CreateDate;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Division_ID;
}
