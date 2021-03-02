package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    @FXML private TableView tableView;
    @FXML private TableColumn  firstNameColumn;
    @FXML private TableColumn lastNameColumn;
    @FXML private TableColumn addressColumn;

    private TableView<PersonRecord> people;

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableView.setItems(DataSourceExample.getAllRecords());
    }

}
