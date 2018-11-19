package com.tminions.app;

import com.tminions.app.controllers.ConflictsController;
import com.tminions.app.models.ConflictModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

public class ConflictsScreenController {

    // Used to hold current conflict
    private ConflictModel conflict;

    @FXML private Label conflictsText;
    @FXML private TableView<Map<String, String>> conflictingRows;
    @FXML private ScrollPane scroller;
    @FXML private GridPane root;
    @FXML private GridPane grid;

    // Called by javafx framework after screen components have been initialized
    public void initialize() {
        updateScreen();
    }

    public void updateScreen() {
        // Get a conflict
        this.conflict = getConflict();
        // If conflict does not exist, show that no conflicts found
        conflictsText.setText(conflict == null ? "No conflicts found!" : "Please fix the conflict below");
        // Show conflict data if it is not null
        if (conflict != null) {
            // Get map of column names to values for first person in list
            Map<String, String> columnToValues = conflict.getConflicts().get(0);

            // Create a list to hold column constraints for how many people there are + 2 columns for padding

            ObservableList<ColumnConstraints> constraints = FXCollections.observableArrayList();
            // Find number of columns needed + 2 to show values and input
            int size = 100/(conflict.getConflicts().size()+2);
            for (int i = 0; i < conflict.getConflicts().size() + 2; i++) {
                ColumnConstraints col = new ColumnConstraints();
                col.setPercentWidth(size);
                col.setPrefWidth(size);
                constraints.add(col);
            }
            grid.getColumnConstraints().setAll(constraints);
            scroller.setFitToWidth(true);

            // Get column names
            LinkedHashSet<String> columnNames = new LinkedHashSet<>(conflict.getConflicts().get(0).keySet());

            // Add key title
            int currRow = columnNames.size() - 1;
            grid.add(new Label("Field"), 0, 0);
            // Loop through each column name adding it first column
            for (String name : columnNames) {
                grid.add(new Label(name), 0, currRow + 1);
                currRow--;
            }

            int currCol = conflict.getConflicts().size() - 1;
            // Loop through each person
            for (Map<String, String> person : conflict.getConflicts()) {
                currRow = columnNames.size() - 1;
                // Add person heading
                grid.add(new Label("Person " + Integer.toString(currCol)), currCol + 1, 0);
                // Add all their values for the current column
                for (String column : columnNames) {
                    // Add their value for the current column
                    grid.add(new Label(person.get(column)), currCol + 1, currRow + 1);
                    currRow--;
                }
                currCol--;
            }

            // Add resolved title
            currRow = columnNames.size() - 1;
            grid.add(new Label("Resolved"), conflict.getConflicts().size() + 1, 0);
            // Create array of textfields to hold resolved values
            TextField[] inputs = new TextField[columnNames.size()];
            int currInput = 0;
            // Loop through each column name
            for (String name : columnNames) {
                // Create textfield
                TextField input = new TextField();
                input.setPromptText(name + " RESOLVED");
                inputs[currInput] = input;
                grid.add(input, conflict.getConflicts().size() + 1, currRow + 1);
                currRow--;
            }

//              Possible TableView implementation
//            // Find column names and add to table
//            for (String columnName : columnToValues.keySet()) {
//                // Create new column
//                TableColumn<Map<String, String>, String> curr = new TableColumn(columnName);
//                // Whenever a cell is needed, check the property
//                curr.setCellValueFactory(person -> new SimpleStringProperty(person.getValue().get(columnName)));
//                // Ensure that the cell maps to the right property
//
//
//                curr.impl_setReorderable(false);
//                curr.prefWidthProperty().bind(conflictingRows.widthProperty().divide(columnToValues.size()));
//                curr.setSortable(false);
//                conflictingRows.getColumns().add(curr);
//
//            }
//            // loop through each person adding them as a row
//            for (Map<String, String> person : conflict.getConflicts()) {
//                conflictingRows.getItems().add(person);
//            }

        }

    }

    private ConflictModel getConflict() {
        Map<String, String> vals = new HashMap<String, String>(){
            {
                this.put("col1", "val1");
                this.put("col2", "val2");
                this.put("col3", "val3");
                this.put("col4", "val4");
            }
        };
        Map<String, String> vals2 = new HashMap<String, String>(){
            {
                this.put("col1", "val4");
                this.put("col2", "val5");
                this.put("col3", "val6");
                this.put("col4", "val7");
            }
        };
        Map<String, String> vals3 = new HashMap<String, String>(){
            {
                this.put("col1", "val123");
                this.put("col2", "val123");
                this.put("col3", "val123");
                this.put("col4", "val123");
            }
        };
        Map<String, String> vals4 = new HashMap<String, String>(){
            {
                this.put("col1", "val123");
                this.put("col2", "val123");
                this.put("col3", "val123");
                this.put("col4", "val123");
            }
        };
        Map<String, String> vals5 = new HashMap<String, String>(){
            {
                this.put("col1", "val123");
                this.put("col2", "val123");
                this.put("col3", "val123");
                this.put("col4", "val123");
            }
        };
        Map<String, String> vals6 = new HashMap<String, String>(){
            {
                this.put("col1", "val123");
                this.put("col2", "val123");
                this.put("col3", "val123");
                this.put("col4", "val123");
            }
        };
        ConflictModel a = new ConflictModel("id", "template", "unique id",
                Arrays.asList(vals, vals2));
        return a;
//        return ConflictsController.getConflict();
    }

}