package core.controllersAndPages;

import core.classes.Inventory;
import core.classes.Part;
import core.classes.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;


public class MainController {
    /**
     * todo section
     * part double value needs two decimal places
     */

    private Inventory inventory;

    @FXML
    private Button main_add_part_btn, product_add_btn;

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
        populateDisplay();
    }

    /**
     * creates a new inventory object.
     */
    @FXML
    public void initialize(){ this.inventory = new Inventory(); }

    /**
     * populates the tables in the main view.
     */
    private void populateDisplay(){
        //parts items
        part_id_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        part_name_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        part_inventory_col.setCellValueFactory(data-> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        part_price_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        main_parts_table_view.setItems( inventory.getAllParts());

        //product items
        product_id_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        product_name_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        product_inventory_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        product_price_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        main_product_table_view.setItems(inventory.getAllProducts());
    };

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

    }
}
