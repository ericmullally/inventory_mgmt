package core.controllersAndPages;

import core.classes.Inventory;
import core.classes.Part;
import core.classes.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.FXPermission;

import java.io.IOException;


/**
 * controls the main page of the application.
 *
 * RUNTIME ERROR: Could not get the part or product table to populate the integer values.
 * solution. about 30 minutes of searching and i found that i needed to set the simple integer property as objects.
 *
 * FUTURE IMPROVEMENT: The save button should send a copy of the inventory to a database to save it.
 */
public class MainController {

    private Inventory inventory;

    @FXML
    private Button main_add_part_btn, product_add_btn, main_exit_btn;

    @FXML
    private TextField part_search_box, product_search_box;

    //<editor-fold desc="partsTable">
    @FXML
    private TableView<Part> main_parts_table_view;
    @FXML
    private TableColumn<Part, Integer> part_id_col;
    @FXML
    private TableColumn<Part, String> part_name_col;
    @FXML
    private TableColumn<Part, Integer> part_inventory_col;
    @FXML
    private TableColumn<Part, Double> part_price_col;
    //</editor-fold>

    //<editor-fold desc="productTable">
    @FXML
    private TableView<Product> main_product_table_view;
    @FXML
    private TableColumn<Product, Integer> product_id_col;
    @FXML
    private TableColumn<Product, String> product_name_col;
    @FXML
    private TableColumn<Product, Integer> product_inventory_col;
    @FXML
    private TableColumn<Product, Double> product_price_col;
    //</editor-fold>

    /**
     *
     * @param inventory1 updated inventory
     *  sets the current inventory to the updated inventory.
     *  calls populateDisplay.
     */
    @FXML
    public void initialize(Inventory inventory1){
        this.inventory = inventory1;
        populateParts(inventory.getAllParts());
        populateProducts(inventory.getAllProducts());
    }

    /**
     * creates a new inventory object.
     */
    @FXML
    public void initialize(){
        part_search_box.textProperty().addListener(( observableValue, oldVal, newVal)->{

            ObservableList<Part> toDisplay = FXCollections.observableArrayList();

            if(newVal.isEmpty()){
                toDisplay = inventory.getAllParts();
            }else{
                try{
                   int partId = Integer.parseInt(newVal);
                   toDisplay.add(inventory.lookUpPart(partId));
                }catch(Exception ex){
                    toDisplay = inventory.lookUpPart(newVal);
                }
            }
            if(toDisplay.isEmpty()){
                Alert noPartFound = new Alert(Alert.AlertType.ERROR);
                noPartFound.setHeaderText("Part Not Found");
                noPartFound.setContentText(String.format("No parts match %s", newVal));
                noPartFound.initModality(Modality.APPLICATION_MODAL);
                noPartFound.show();
                return;
            }else{
                populateParts(toDisplay);
            }

        });
        product_search_box.textProperty().addListener(( observableValue, oldVal, newVal)->{

            ObservableList<Product> toDisplay = FXCollections.observableArrayList();

            if(newVal.isEmpty()){
                toDisplay = inventory.getAllProducts();
            }else{
                try{
                    int productId = Integer.parseInt(newVal);
                    toDisplay.add(inventory.lookUpProduct(productId));
                }catch(Exception ex){
                    toDisplay = inventory.lookUpProduct(newVal);
                }
            }
            if(toDisplay.isEmpty()){
                Alert noProductFound = new Alert(Alert.AlertType.ERROR);
                noProductFound.setHeaderText("Product Not Found");
                noProductFound.setContentText(String.format("No products match %s", newVal));
                noProductFound.initModality(Modality.APPLICATION_MODAL);
                noProductFound.show();
                return;
            }else{
                populateProducts(toDisplay);
            }

        });
        this.inventory = new Inventory();
    }

    /**
     * @param list products to display.
     * populates the Product table in the main view.
     */
    private void populateProducts(ObservableList list){
        product_id_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        product_name_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        product_inventory_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        product_price_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        main_product_table_view.setItems(list);
    };

    /**
     *
     * @param list parts to display
     *  populates the parts table in the main view.
     */
    private void populateParts(ObservableList list){
        part_id_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        part_name_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        part_inventory_col.setCellValueFactory(data-> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        part_price_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        main_parts_table_view.setItems( list);
    }


    /**
     * @param actionEvent is unused.
     * @throws IOException
     * Opens the add Parts Page
     */
    public void onAddPartClick(ActionEvent actionEvent) throws IOException {
            FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource("pages/AddPart.fxml"));
            Parent root = addPartLoader.load();
            PartController addPartController = addPartLoader.getController();
            addPartController.initialize(inventory);
            Stage partStage = new Stage();
            partStage.setScene(new Scene(root));
            partStage.setTitle("Add Part");
            Stage currentStage = (Stage)main_add_part_btn.getScene().getWindow();
            partStage.setOnCloseRequest(windowEvent -> {
                partStage.close();
                currentStage.show();
            });

            partStage.show();
            currentStage.hide();

    }

    /**
     *
     * @param actionEvent is unused
     * @throws IOException
     * displays part edit page.
     */
    public void onEditPartClick(ActionEvent actionEvent) throws IOException{
        Part partToEdit = main_parts_table_view.getSelectionModel().getSelectedItem();

        if(partToEdit == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setHeaderText("No part selected");
            noSelection.setContentText("Please select a part to edit by clicking on it.");
            noSelection.initModality(Modality.APPLICATION_MODAL);
            noSelection.showAndWait();
            return;
        }

        FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource("pages/AddPart.fxml"));
        Parent root = addPartLoader.load();
        PartController addPartController = addPartLoader.getController();
        addPartController.initialize(inventory, partToEdit.getId());
        Stage partStage = new Stage();
        partStage.setScene(new Scene(root));
        partStage.setTitle("Edit Part");
        Stage currentStage = (Stage)main_add_part_btn.getScene().getWindow();
        partStage.setOnCloseRequest(windowEvent -> {
            partStage.close();
            currentStage.show();
        });

        partStage.show();
        currentStage.hide();
    }

    /**
     * removes a part form the inventory and display.
     * also removes the part form any product that it is associated with.
     * if part is not removed displays an error message.
     */
    public void onDeletePartClick(){
        Part partToDelete = main_parts_table_view.getSelectionModel().getSelectedItem();
        if(partToDelete == null){
            Alert partSelectionError = new Alert(Alert.AlertType.ERROR);
            partSelectionError.setHeaderText("No Part Selected");
            partSelectionError.setContentText("There is no part selected to delete.");
        }else{
            Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
            conformation.setHeaderText("Confirmation");
            conformation.setContentText(String.format( "Are you sure you want to delete part: %d ", partToDelete.getId()));
            conformation.showAndWait();
            boolean response =  conformation.getResult().getButtonData().isDefaultButton();


            if(response){
                //checking all the products to see if the part is associated.
                // I guess we don't want this.
                //but I will leave it in case I misunderstood.

//                ObservableList<Product> inventoryToCheck =inventory.getAllProducts();
//                inventoryToCheck.forEach(item ->{
//                    if(item.getId() == partToDelete.getId()){
//                        item.deleteAssociatedPart(partToDelete.getId());
//                    }
//                });

                // remove the part from main inventory.
                boolean wasRemoved = inventory.deletePart(partToDelete);
                if(!wasRemoved){
                    Alert failed = new Alert(Alert.AlertType.ERROR);
                    failed.setHeaderText("Part not removed.");
                    failed.setContentText("Part was not successfully removed.");
                    return;
                }
                populateParts(inventory.getAllParts());
            }else{
                return;
            }
        }
    }

    /**
     * @param actionEvent
     * @throws IOException
     * Opens the add Product Page when the add button is clicked,
     * in the product section of the main page
     */
    public void onAddProductClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader addProductLoader = new FXMLLoader(getClass().getResource("pages/AddProduct.fxml"));
        Parent root = addProductLoader.load();
        ProductController addProductController =  addProductLoader.getController();
        addProductController.initialize(this.inventory);
        Stage productStage = new Stage();
        productStage.setScene(new Scene(root));
        productStage.setTitle("Add Product");

        Stage currentStage = (Stage)product_add_btn.getScene().getWindow();
        productStage.setOnCloseRequest(event ->{
            productStage.close();
            currentStage.show();
        });

        currentStage.hide();
        productStage.show();
    }

    /**
     *
     * @param actionEvent is unused
     * @throws IOException
     * displays product edit page.
     */
    public void onEditProductClick(ActionEvent actionEvent) throws IOException{
        Product selectedProduct = main_product_table_view.getSelectionModel().getSelectedItem();
        if(selectedProduct == null){
            Alert missingProduct = new Alert(Alert.AlertType.ERROR);
            missingProduct.setHeaderText("No product selected");
            missingProduct.setContentText("Please select a product to edit");
            missingProduct.initModality(Modality.APPLICATION_MODAL);
            missingProduct.show();
            return;
        }

        FXMLLoader addProductLoader = new FXMLLoader(getClass().getResource("pages/AddProduct.fxml"));
        Parent root = addProductLoader.load();
        ProductController addProductController =  addProductLoader.getController();
        addProductController.initialize(this.inventory, selectedProduct.getId());
        Stage productStage = new Stage();
        productStage.setScene(new Scene(root));
        productStage.setTitle("Edit Product");

        Stage currentStage = (Stage)product_add_btn.getScene().getWindow();
        productStage.setOnCloseRequest(event ->{
            productStage.close();
            currentStage.show();
        });

        currentStage.hide();
        productStage.show();
    }

    /**
     *
     * @param actionEvent
     * Removes the associated product.
     */
    public void onDeleteProductClick(ActionEvent actionEvent){
       Product productToRemove = main_product_table_view.getSelectionModel().getSelectedItem();
       if(productToRemove == null){
           Alert noSelectionError = new Alert(Alert.AlertType.ERROR);
           noSelectionError.setHeaderText("No product selected");
           noSelectionError.setContentText("Please select a product.");
           noSelectionError.initModality(Modality.APPLICATION_MODAL);
           noSelectionError.show();
           return;
       }else if(productToRemove.getAllAssociatedParts().size() >0){
           Alert associatedPartAlert = new Alert(Alert.AlertType.ERROR);
           associatedPartAlert.setHeaderText("Associated Part");
           associatedPartAlert.setContentText(String.format("The product %s has an associated part. \n Please remove all associated parts before deleting", productToRemove.getName()));
           associatedPartAlert.initModality(Modality.APPLICATION_MODAL);
           associatedPartAlert.show();
           return;
       }else {
           if(productToRemove.getAllAssociatedParts().size()>0){
               Alert associatedPartAlert = new Alert(Alert.AlertType.ERROR);
               associatedPartAlert.setHeaderText("Associated Part");
               associatedPartAlert.setContentText(String.format("The product %s has an associated part. \n Please remove all associated parts before deleting", productToRemove.getName()));
               associatedPartAlert.initModality(Modality.APPLICATION_MODAL);
               associatedPartAlert.show();
               return;
           }
           Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
           conformation.setHeaderText("Confirmation");
           conformation.setContentText(String.format("Are you sure you want to delete Product: %d ?", productToRemove.getId()));
           conformation.initModality(Modality.APPLICATION_MODAL);
           conformation.showAndWait();

           boolean response =  conformation.getResult().getButtonData().isDefaultButton();
           if(response){
            boolean removed = inventory.deleteProduct(productToRemove);
            if(!removed){
                Alert removalError = new Alert(Alert.AlertType.ERROR);
                removalError.setHeaderText("Removal Error");
                removalError.setContentText(String.format("Unable to delete Product: %d", productToRemove.getId()));
                removalError.initModality(Modality.APPLICATION_MODAL);
                removalError.show();
            }
           }
           populateProducts(inventory.getAllProducts());
       }
    }

    public void onExitClick(){
       Stage mainWindow = (Stage) main_exit_btn.getScene().getWindow();
       Alert conformation = new Alert(Alert.AlertType.CONFIRMATION);
       conformation.setHeaderText("Exit Application");
       conformation.setContentText("Are you sure you wish to Exit?");
       conformation.initModality(Modality.APPLICATION_MODAL);
       conformation.showAndWait();
       Boolean response = conformation.getResult().getButtonData().isDefaultButton();
       if(response){
           mainWindow.close();
       }else{
           return;
        }

    }


}
