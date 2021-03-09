<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.GridPane?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TableColumn?>

<GridPane fx:controller="SpamDetection.Controller"
          xmlns:fx="http://javafx.com/fxml" alignment="center" hgap="10" vgap="10" style="-fx-background-color: #0000FF;">

    <Label text="Spam Detection System" GridPane.columnIndex="0" GridPane.rowIndex="0" style=
            "-fx-font-size: 48 arial;
            -fx-font-weight: bold;
            -fx-color: #FFFFFF;
            -fx-background-color: #FFFFFF;
            -fx-alignment: center;"/>

    <TableView fx:id="tableView"  GridPane.columnIndex="0" GridPane.rowIndex="1" maxHeight="720" maxWidth="1280" editable="false" minHeight="480" minWidth="560" prefHeight="480" prefWidth="560">
       <columns>
           <TableColumn fx:id="fileColumn" text="Student Number"></TableColumn>
           <TableColumn fx:id="actualClassColumn" text="Actual Class"></TableColumn>
           <TableColumn fx:id="spamProbabilityColumn" text="Spam Probability"></TableColumn>
           <TableColumn fx:id="classGuessColumn" text="Calculated Class"></TableColumn>

       </columns>

    </TableView>

    <Label fx:id="accuracyLabel" text="" GridPane.ColumnIndex="0" GridPane.RowIndex="2" style=
            "-fx-font-size: 24 arial;
            -fx-font-weight: bold;
            -fx-color: #FFFFFF;
            -fx-background-color: #FFFFFF;
            -fx-alignment: center;"/>
    
    <Label fx:id="precisionLabel" text="" GridPane.ColumnIndex="0" GridPane.RowIndex="3" style=
            "-fx-font-size: 24 arial;
            -fx-font-weight: bold;
            -fx-color: #FFFFFF;
            -fx-background-color: #FFFFFF;
            -fx-alignment: center;"/>

</GridPane>
