package Lab05;

import Lab05.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    @FXML private TableView tableView;
    @FXML private TableColumn studentNumberColumn;
    @FXML private TableColumn midtermExamColumn;
    @FXML private TableColumn assignmentsColumn;
    @FXML private TableColumn finalExamColumn;
    @FXML private TableColumn finalMarkColumn;
    @FXML private TableColumn letterGradeColumn;

    /*
    @FXML private TextField studentNumberField;
    @FXML private TextField assignmentsField;
    @FXML private TextField midtermExamField;
    @FXML private TextField finalExamField;

    @FXML private Button addButton;
    */

    private TableView<StudentRecord> students;

    public Controller() {

    }

    @FXML
    public void initialize() {
        studentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        midtermExamColumn.setCellValueFactory(new PropertyValueFactory<>("midtermExam"));
        assignmentsColumn.setCellValueFactory(new PropertyValueFactory<>("assignments"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMarkColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        tableView.setItems(DataSource.getAllMarks());
        tableView.refresh();

    }



     /*
    public void buttonOnPress(ActionEvent event) {
        if ((studentNumberField.getText().length() > 0) && (assignmentsField.getText().length() > 0)
                && (midtermExamField.getText().length() > 0) && (finalExamField.getText().length() > 0)) {

            // obtain inputs
            // convert the marks to floats as they get inputted
            String studentNumber = studentNumberField.getText();
            float assignments = Float.parseFloat(assignmentsField.getText());
            float midtermExam = Float.parseFloat(midtermExamField.getText());
            float finalExam = Float.parseFloat(finalExamField.getText());

            System.out.println(studentNumber);
            System.out.println(assignments);
            System.out.println(midtermExam);
            System.out.println(finalExam);

            DataSource.getAllMarks().add(new StudentRecord(studentNumber, assignments, midtermExam, finalExam));

            int size = DataSource.getAllMarks().size();
            System.out.println("DataSource.getAllMarks() Size: " + size);

            tableView.refresh();

        }
    }

    */

}
