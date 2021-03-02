package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataSourceExample {
    public static ObservableList<PersonRecord> getAllRecords() {
        ObservableList<PersonRecord> records = FXCollections.observableArrayList();

        records.add(new PersonRecord("Kevin", "D", "Test"));
        records.add(new PersonRecord("Kevin", "D", "Test"));
        records.add(new PersonRecord("Example", "Example", "Example"));

        return records;
    }
}
