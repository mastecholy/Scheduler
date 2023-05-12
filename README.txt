This application all related files were constructed and formatted to conspicuously satisfy the requirements listed in the rubric, intended to include all necessary configurations to be ran on the evaluator's standardized VM environment.



TITLE AND PURPOSE OF APPLICATION:
    This application is called "Scheduler" and its purpose is to allow the user to create, remove, update and delete
both customers and appointments within the mysql database. Additionally, it can generate certain reports about the database
information and allows the user to sort customers and appointments within their tables, while being able to filter the
appointments table by month, week, or all.


AUTHOR: Massimiliano Petrizzi
CONTACT INFORMATION: mpetriz@wgu.edu
APPLICATION VERSION: v1.0
DATE: 4/28/2023


IDE: IntelliJ IDEA 2023.1 (Community Edition)
JDK: Runtime version: 17.0.6+10-b829.5 aarch64
JAVAFX VERSION: JavaFX-SDK-17.0.1


DIRECTIONS TO RUN PROGRAM:
    Upon application startup, you will be prompted with a login screen. To log in you can use username "sqlUser"
and password "Passw0rd!" or root user information for the locally hosted database.
    After successfully logging in, you will be taken to the directory form and presented with a greeting alert.
The alert will let you know if there are any appointments starting within 15 minutes.
    After closing the alert, you will be able to view customer and appointment information within their respective tables,
with the ability to filter appointments by all, the current month, or the current calendar week using radio buttons
above the table.
    You can create a customer by clicking the 'Add' button above the customer table, which will take you to a new form to
input customer information. Likewise, you can edit a customer by clicking the 'Edit' button above the customer table which will take you
to a similar form, but with the customer's information populated into all of the fields. You can also delete a selected
customer with the 'Remove' button. Doing so will delete all appointments associated with the selected customer.
    You can create an appointment by clicking the 'Add' button above the appointments table, which will take you to
a new form to input appointment information. You can select dates with the date pickers and use the spinners to select
the hour and minute for both the start and end time. Likewise, you can edit an appointment by clicking the 'Edit' button
above the appointment table which will take you to a similar form with the customer's information populated into all of
the fields. You can also delete a selected appointment with the 'Remove' button.
    Also in the directory, you can generate three different reports by clicking the 'Reports' button. This will prompt
you to select one of three reports to generate, and the reports will show in dialog boxes.
    Clicking the 'Logout' button will close the database connection and return you to the Login screen.
    Clicking the 'Exit Program' button will close the database connection and terminate the program.


ADDITIONAL REPORT DESCRIPTION:
    The additional report I chose to have the application generate displays the customer or customers with the
most scheduled appointments, as well as their number of scheduled appointments. This could be useful for a
business that would like to determine their most valuable or active customer.


MYSQL CONNECTOR DRIVER VERSION: mysql-connector-j-8.0.32

