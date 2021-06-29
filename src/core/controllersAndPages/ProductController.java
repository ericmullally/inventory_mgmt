package core.controllersAndPages;

import core.classes.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.text.MessageFormat;


/**
 * controls the product add or edit page
 *
 * RUNTIME EXCEPTION: In onAddAssociatedPartClick: Null Pointer Exception.
 * I was referencing the wrong table to get the selections.
 * CORRECTION: I changed the table view name to the correct one.
 *
 * IMPROVEMENTS: The user is unable to deselect a part by clicking it again.
 * a click Listener could be added to the table for each element, so that if it is clicked when already selected
 * it will be deselected.
 */

public class ProductController {
    private Inventory inventory;
    private ObservableList<Part> partsToAdd = FXCollections.observableArrayList();

    int Id = -1;

    @FXML
    private TextField  add_product_id_field, add_product_name_field, add_product_inventory_field, add_product_price_field,
            add_product_max_field, add_product_min_field;

    @FXML
    private Button add_product_save_btn, add_product_cancel_btn;

    //<editor-fold desc="table elements">
    @FXML
    private TableView<Part> add_product_parts_selection_table, add_product_associated_parts_table;

    @FXML
    private TableColumn<Part, Integer> part_selection_id, associated_part_id, associated_part_inventory, part_selection_inventory;

    @FXML
    private TableColumn<Part, String> part_selection_name, associated_part_name;

    @FXML
    private TableColumn<Part, Double> part_selection_price, associated_part_price;
    //</editor-fold>

    /**
     *
     * @param inventory main inventory element to add the new product to.
     * creates the new products ID. sets part selection mode. populates part selection table.
     */
    @FXML
    public void initialize(Inventory inventory){
        this.inventory = inventory;
        this.Id = inventory.getAllProducts().size() + 1;
        add_product_id_field.setText(String.valueOf(this.Id));
        add_product_parts_selection_table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        populateSelectionTable();
    }

    /**
     *
     * @param actionEvent is unused.
     * @throws IOException exceptions are thrown in isValid and is complete methods.
     * Creates new products class outsourced or inhouse depending on current selection.
     * Creates new main page and gives it thee new inventory object with the new products.
     */
    @FXML
    private void onSaveProductClick (ActionEvent actionEvent) throws IOException {
        boolean isComplete= checkFields();
        boolean isValid =  checkValidInput();

        if(isComplete && isValid){
            String name = add_product_name_field.getText();
            int inv = Integer.parseInt(add_product_inventory_field.getText().strip());
            double price = Double.parseDouble(add_product_price_field.getText().strip());
            int max = Integer.parseInt(add_product_max_field.getText().strip());
            int min = Integer.parseInt(add_product_min_field.getText().strip());

            Product newProduct = new Product(this.Id, name, price, inv,  max, min );
            partsToAdd.forEach(part->{ newProduct.addAssociatedPart(part); });
            this.inventory.addProduct(newProduct);

            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("pages/MainPage.fxml"));
            Parent root = mainLoader.load();
            MainController mainController = mainLoader.getController();
            mainController.initialize(this.inventory);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Inventory Management System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            Stage currentStage = (Stage) add_product_save_btn.getScene().getWindow();
            currentStage.close();
        }

    }

    /**
     *
     * @param actionEvent is unused.
     * @throws IOException
     * Exits add products window, and redirects back to main.
     * does not save current products.
     */
    @FXML
    private void onCancelClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("pages/MainPage.fxml"));
        Parent root = mainLoader.load();
        MainController mainController = mainLoader.getController();
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Inventory Management System");
        mainController.initialize(this.inventory);
        mainStage.show();

        Stage currentStage = (Stage) add_product_cancel_btn.getScene().getWindow();
        currentStage.close();
    }

    /**
     *
     * @param actionEvent is unused
     * Adds selected Parts to the items to add list. if they are not already present.
     * then re-populates the associated parts list.
     */
    @FXML
    private void onAddAssociatedPartClick( ActionEvent actionEvent){
       ObservableList<Part> items =  add_product_parts_selection_table.getSelectionModel().getSelectedItems();
       for(Part item : items){
            if(!partsToAdd.contains(item)){
                partsToAdd.add(item);
            }
       }
       populateAssociatedParts();
    }

    /**
     *
     * @param actionEvent is unused
     * Shows alert to confirm the user wishes to remove the part.
     *  removes the selected part from the items to add list.
     */
    @FXML
    private void onAssociatedRemoveClick(ActionEvent actionEvent){
        Part item =  add_product_associated_parts_table.getSelectionModel().getSelectedItem();
        int partId = item.getId();
        boolean response;
        Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
        conformation.setHeaderText("Please Confirm");
        conformation.setContentText(String.format("Are you sure you want to remove Associated part ID:  %d", partId ));
        conformation.initModality(Modality.APPLICATION_MODAL);
        conformation.showAndWait();
        response =  conformation.getResult().getButtonData().isDefaultButton();

        if(response){
                partsToAdd.remove(item);
        }else{
            return;
        }

        populateAssociatedParts();
    }

    /**
     * @return true if all fields are filled, else false.
     *
     * Checks that all required fields have been filled.
     * Shows an alert identifying the first found empty field, if necessary.
     */
    private boolean checkFields(){

        TextField[] fields = {add_product_name_field,  add_product_inventory_field, add_product_price_field, add_product_max_field,
                add_product_min_field};

        for(TextField field : fields){
            if(field.getText().isEmpty()){

                String[] fieldNameList = field.getId().split("_");
                String fieldName = "product " + fieldNameList[fieldNameList.length -2];
                String information = MessageFormat.format("The following Field is empty: {0}", fieldName);

                Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                errorMsg.setHeaderText("Empty Field");
                errorMsg.setContentText(information);
                errorMsg.show();
                return false;
            }
        }
        return true;

    }

    /**
     * @return False if any field doesn't contain the proper value.
     *
     * Checks that all fields requiring integer or float contain the proper value.
     * Shows an alert identifying the effected field.
     */
    private boolean checkValidInput(){
        TextField[] fieldsToCheck = { add_product_inventory_field, add_product_price_field, add_product_max_field,
                add_product_min_field};
        int min = 0;
        int max = 0;
        int inventory = 0;

        for(TextField field : fieldsToCheck){
            switch (field.getId()) {

                case "add_product_inv_field":
                    try {
                        inventory = Integer.parseInt(field.getText().strip());
                    } catch (Exception ex) {
                        Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                        errorMsg.setHeaderText("Invalid Input");
                        errorMsg.setContentText("An integer value is required for inventory section.");
                        errorMsg.show();
                        return false;
                    }
                    break;

                case "add_product_price_field":
                    try {
                        Double.parseDouble(field.getText().strip());
                    } catch (Exception ex) {
                        Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                        errorMsg.setHeaderText("Price Invalid");
                        errorMsg.setContentText("Please enter a valid price.");
                        errorMsg.show();
                        return false;
                    }
                    break;

                case "add_product_max_field":
                    try{
                        max = Integer.parseInt(field.getText().strip());
                    }catch(Exception ex){
                        Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                        errorMsg.setHeaderText("Maximum Value Invalid");
                        errorMsg.setContentText("Please enter an integer value for maximum inventory.");
                        errorMsg.show();
                        return false;
                    }
                    break;

                case "add_product_min_field":
                    try{
                        min = Integer.parseInt(field.getText().strip());
                    }catch(Exception ex){
                        Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                        errorMsg.setHeaderText("Minimum Value Invalid");
                        errorMsg.setContentText("Please enter an integer value for minimum inventory.");
                        errorMsg.show();
                        return false;
                    }
                    break;

                default:
                    System.out.println("You have made a mistake in product controller check valid input.");
                    return false;

            }

        }


        if(min > max){
            Alert errorMsg = new Alert(Alert.AlertType.ERROR);
            errorMsg.setHeaderText("Invalid Input");
            errorMsg.setContentText("The minimum number of products should be less than the maximum.");
            errorMsg.show();
            return false;
        }

        if(inventory < min || inventory > max){
            boolean underInv = inventory < min;
            String overUnder = underInv ? "Less Than Min" : "Greater Than Max";

            String info = String.format("Inventory is %s",overUnder );
            Alert errorMsg = new Alert(Alert.AlertType.ERROR);
            errorMsg.setHeaderText("Invalid Input");
            errorMsg.setContentText(info);
            errorMsg.show();
            return false;
        }

        return true;
    }

    /**
     * fills the parts selection table.
     */
    private void populateSelectionTable(){
        part_selection_id.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        part_selection_name.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        part_selection_inventory.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        part_selection_price.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        add_product_parts_selection_table.setItems(inventory.getAllParts());
    }

    /**
     * populates the associated parts table.
     */
    private void populateAssociatedParts(){
        associated_part_id.setCellValueFactory(data-> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        associated_part_name.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getName()));
        associated_part_inventory.setCellValueFactory(data-> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        associated_part_price.setCellValueFactory(data-> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        add_product_associated_parts_table.setItems(this.partsToAdd);
    }

}
