package SpamDetection;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class Controller {

    @FXML private TableView tableView;
    @FXML private TableColumn fileColumn;
    @FXML private TableColumn actualClassColumn;
    @FXML private TableColumn spamProbabilityColumn;
    @FXML private TableColumn classGuessColumn;

    @FXML private TableView<SpamReport> reports;

    @FXML Label accuracyLabel;
    @FXML Label precisionLabel;

    public Controller() {

    }

    @FXML public void initialize() throws IOException {

        ExecutionCallable.execute();

        fileColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        actualClassColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbabilityColumn.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));
        classGuessColumn.setCellValueFactory(new PropertyValueFactory<>("classGuess"));

        tableView.setItems(DataSource.getAllReports());
        tableView.refresh();

        float accuracy = 0.00000f;
        accuracy = (float) DataSource.getAccuracy();
        accuracy = (float) (accuracy * 100.00000f);

        float precision = 0.00000f;
        precision = (float) DataSource.getPrecision();
        precision = (float) (precision * 100.00000f);

        System.out.println("Accuracy: " + accuracy + "%");
        System.out.println("Precision: " + precision + "%");

        accuracyLabel.setText("Accuracy: " + String.valueOf(accuracy) + "%");
        precisionLabel.setText("Precision: " + String.valueOf(precision) + "%");

        DataSource.printStuff();


    }



}
