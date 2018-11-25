package com.tminions.app;

import com.tminions.app.controllers.ConflictsController;
import com.tminions.app.models.ConflictModel;
import com.tminions.app.models.ResolvedConflictModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.*;


public class ConflictsScreenController {

    // Used to hold current conflict
    private ConflictModel conflict;

    @FXML private Label conflictsText;
    @FXML private ScrollPane scroller;
    @FXML private GridPane grid;
    @FXML private Button submit;
    @FXML private Button copy;

    // Called by javafx framework after screen components have been initialized
    public void initialize() {
        updateScreen();
    }

    public void updateScreen() {
        // Get a conflict
        this.conflict = getConflict();
        System.out.println(conflict);
        // If conflict does not exist, show that no conflicts found
        conflictsText.setText(conflict == null ? "No conflicts found!" : "Please fix the conflict below in Template: " + conflict.getTemplate_name());
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
            List<String> columnNames = new ArrayList<>(conflict.getConflicts().get(0).keySet());

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
                grid.add(new Label("Person " + Integer.toString(currCol + 1)), currCol + 1, 0);
                // Add all their values for the current column
                for (String column : columnNames) {
                    // Add their value for the current column
                    TextField field = new TextField(person.get(column));
                    field.setEditable(false);
                    grid.add(field, currCol + 1, currRow + 1);
                    currRow--;
                }
                currCol--;
            }

            // Add resolved title
            currRow = columnNames.size() - 1;
            grid.add(new Label("Resolved"), conflict.getConflicts().size() + 1, 0);
            // Create array of textfields to hold resolved values
            List<TextField> inputs = new ArrayList<TextField>();
            int currInput = 0;
            // Loop through each column name
            for (String name : columnNames) {
                // Create textfield
                TextField input = new TextField();
                input.setPromptText(name + " RESOLVED");
                inputs.add(input);
                grid.add(input, conflict.getConflicts().size() + 1, currRow + 1);
                currRow--;
            }

            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    if (submitUpdate(columnNames, inputs)) {
                        AlertBox.display("Update Successful!", "Update has been sent");
                        updateScreen();
                    } else {
                        AlertBox.display("Update Failed!", "The update was unsuccessful at this time, please try again later.");
                    }
                }
            });

            copy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int i = 0;
                    for (String column : columnNames) {
                        //if all the persons.get(column) is the same, then set inputs.get(i) to the value
                        if (conflict.getConflicts().get(0).get(column).equals(conflict.getConflicts().get(1).get(column))) {
                            inputs.get(i).setText(conflict.getConflicts().get(0).get(column));
                        }
                        i++;
                    }
                }
            });

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

        } else {
            scroller.setVisible(false);
            grid.setVisible(false);
            submit.setVisible(false);
            copy.setVisible(false);
        }

    }

    /**
     * Call this to try and submit data.
     * @param columnNames List of column names
     * @param fields Array of textfields. The textfield order must correspond to the column names.
     * @return True when successful request to server, false otherwise.
     */
    private boolean submitUpdate(List<String> columnNames, List<TextField> fields) {
        // Create map of resolved columns
        Map<String, String> resolvedColumns = new HashMap<String, String>();
        // Loop through each column
        for (int i = 0; i < columnNames.size(); i++) {
            resolvedColumns.put(columnNames.get(i), fields.get(i).getText());
        }

        ResolvedConflictModel resolved = new ResolvedConflictModel(conflict.get_id(), conflict.getTemplate_name(),
                conflict.getUnique_identifier(), resolvedColumns);
        return ConflictsController.resolveConflict(resolved);
    }

    private ConflictModel getConflict() {
        // The following below can be uncommented to test the feature
//        Map<String, String> vals = new HashMap<String, String>(){
//            {
//                this.put("col1", "val1");
//                this.put("col2", "val2");
//                this.put("col3", "val3");
//                this.put("col4", "val4");
//            }
//        };
//        Map<String, String> vals2 = new HashMap<String, String>(){
//            {
//                this.put("col1", "val4");
//                this.put("col2", "val5");
//                this.put("col3", "val6");
//                this.put("col4", "val7");
//            }
//        };
//        ConflictModel a = new ConflictModel("id", "template", "unique id",
//                Arrays.asList(vals, vals2));
//        return a;
        return ConflictsController.getConflict();
    }

}