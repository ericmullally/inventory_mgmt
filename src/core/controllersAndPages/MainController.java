package core.controllersAndPages;

import core.classes.Inventory;
import core.classes.Part;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;


public class MainController {
    /**
     * todo section
     * on first initialize create a few products and parts.
     * add products to the table on init.
     * part double value needs two decimal places
     * part cancel button needs to send the current inventory back.
     */

    private Inventory inventory;

    @FXML
    private Button main_add_part_btn;

    @FXML
    private TableView<Part> main_parts_table_view;
    @FXML
    private TableColumn<Part,Integer> part_id_col;
    @FXML
    private TableColumn<Part, String> part_name_col;
    @FXML
    private TableColumn<Part, Integer> part_inventory_col;
    @FXML
    private TableColumn<Part, Double> part_price_col;

    @FXML
    public void initialize(Inventory inventory1){
        this.inventory = inventory1;
        populateDisplay();
    }

    @FXML
    public void initialize(){ this.inventory = new Inventory(); }

    /**
     * populates the tables in the main view.
     */
    private void populateDisplay(){
        part_id_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        part_name_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        part_inventory_col.setCellValueFactory(data-> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        part_price_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        main_parts_table_view.setItems( inventory.getAllParts());
    };

    /**
     * @param actionEvent
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
            partStage.initModality(Modality.APPLICATION_MODAL);
            Stage currentStage = (Stage)main_add_part_btn.getScene().getWindow();
            partStage.setOnCloseRequest(windowEvent -> {
                partStage.close();
                currentStage.show();
            });

            partStage.show();
            currentStage.hide();

    }


    /**
     * @param actionEvent
     * @throws IOException
     * Opens the add Product Page when the add button is clicked,
     * in the product section of the main page
     */
    public void onAddProductClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("pages/AddProduct.fxml"));
        Stage productStage = new Stage();
        productStage.setScene(new Scene(root));
        productStage.setTitle("Add Product");
        productStage.initModality(Modality.APPLICATION_MODAL);
        productStage.show();
    }
}
