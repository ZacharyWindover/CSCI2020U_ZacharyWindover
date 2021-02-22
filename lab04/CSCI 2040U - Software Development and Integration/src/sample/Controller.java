package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


public class Controller {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private Button enterBtn;

    private DateTimeFormatter dateTimeFormatter;

    @FXML
    public void initialize() {
        System.out.println("App is running...");

        // format date of birth input
        final String datePattern = "MM/dd/yyyy";
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        dobPicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                String finalDate = null;
                if (date != null) {
                    finalDate = dateTimeFormatter.format(date);
                }
                return finalDate;
            }

            @Override
            // Defines the action to take when converting from a string to a date object
            public LocalDate fromString(String string) {
                LocalDate date = null;

                if (string != null) {
                    date = LocalDate.parse(string, dateTimeFormatter);
                }
                return date;
            }
        });




        // format phone number input


    }

    @FXML
    public void btnOnPress(ActionEvent event) {
        if (nameField.getText().length() > 0) {
            System.out.println("Username: " + usernameField.getText());
            System.out.println("Password: " + passwordField.getText());
            System.out.println("Full Name: " + nameField.getText());
            System.out.println("E-Mail: " + emailField.getText());
            System.out.println("Phone Number: " + phoneField.getText());
            System.out.println("Date of Birth: " + dobPicker.getEditor().getText());
            System.out.println("\n---------------------------------------------------\n");
        }
    }
}

