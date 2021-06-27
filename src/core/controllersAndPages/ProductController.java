package core.controllersAndPages;

import core.classes.InHouse;
import core.classes.Inventory;
import core.classes.Outsourced;
import core.classes.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * controls the product add or edit page
 */

public class ProductController {
    private Inventory inventory;
    int Id = -1;

    @FXML
    private TextField  add_product_id_field, add_product_name_field, add_product_inventory_field, add_product_price_field,
            add_product_max_field, add_product_min_field;

    @FXML
    private Button add_product_save_btn, add_product_cancel_btn;

    @FXML
    public void initialize(Inventory inventory){
        this.inventory = inventory;
        this.Id = inventory.getAllProducts().size() + 1;
        add_product_id_field.setText(String.valueOf(this.Id));


    }

    /**
     *
     * @param actionEvent is unused.
     * @throws IOException exceptions are thrown in isValid and is complete methods.
     * Creates new products class outsourced or inhouse depending on current selection.
     * Creates new main page and gives it thee new inventory object with the new products.
     */
    @FXML
    public void onSaveProductClick (ActionEvent actionEvent) throws IOException {

        boolean isComplete= checkFields();
        boolean isValid =  checkValidInput();

        if(isComplete && isValid){
            String name = add_product_name_field.getText();
            int inv = Integer.parseInt(add_product_inventory_field.getText().strip());
            double price = Double.parseDouble(add_product_price_field.getText().strip());
            int max = Integer.parseInt(add_product_max_field.getText().strip());
            int min = Integer.parseInt(add_product_min_field.getText().strip());

            Product newProduct = new Product(this.Id, name, price, inv,  max, min );
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
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
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





}
