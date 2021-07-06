package core.controllersAndPages;

import core.classes.InHouse;
import core.classes.Inventory;
import core.classes.Outsourced;
import core.classes.Part;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * controller for the parts page.
 *
 * RUNTIME ERROR: after adding the edit functionality I wrote the new edit part function with a foreach statement
 * to check for the matching part and use the setter functions. however I put the if isComplete and isValid check inside the foreach.
 * this cause the program to redirect to the main page if the form was not filled out correctly.
 *
 * SOLUTION: I moved the if isComplete and isValid check to wrap the for each statement and save functionality.
 *
 * IMPROVEMENT:  creating and showing the main page could be extracted to a separate function to make the code more DRY
 *
 */

public class PartController {
    Inventory inventory;
    int Id = -1;
    boolean isEdit;

    @FXML
    private Label add_part_machine_label;

    @FXML
    private TextField add_part_name_field,add_part_inv_field,add_part_price_field,
            add_part_max_field, add_part_min_field, add_part_id_or_company_field, add_part_id_field;

    @FXML
    private RadioButton in_house_btn,outsourced_btn;

    @FXML
    private Button add_part_save_btn, add_part_cancel_btn;

    /**
     * @param inventory sets the edited inventory.
     * Sets toggle buttonGroup, and adds event listener.
     * creates part Id.
     */
    @FXML
    public void initialize(Inventory inventory){
        this.inventory = inventory;
        Id = inventory.getAllParts().size() + 1 ;
        this.isEdit = false;
        add_part_id_field.setText(String.valueOf(Id));

        final ToggleGroup radioBtnGroup = new ToggleGroup();
        in_house_btn.setToggleGroup(radioBtnGroup);
        outsourced_btn.setToggleGroup(radioBtnGroup);
        radioBtnGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (t1 == outsourced_btn) {
                    add_part_machine_label.setText("Company Name");
                } else {
                    add_part_machine_label.setText("Machine ID");
                }
            }
        });
    }

    /**
     *
     * @param inventory inventory object to add the part to.
     * @param Id of the part to be edited.
     */
    @FXML
    public void initialize(Inventory inventory, int Id){
        this.inventory = inventory;
        this.Id = Id;
        this.isEdit = true;
        add_part_id_field.setText(String.valueOf(Id));

        final ToggleGroup radioBtnGroup = new ToggleGroup();
        in_house_btn.setToggleGroup(radioBtnGroup);
        outsourced_btn.setToggleGroup(radioBtnGroup);
        radioBtnGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (t1 == outsourced_btn) {
                    add_part_machine_label.setText("Company Name");
                } else {
                    add_part_machine_label.setText("Machine ID");
                }
            }
        });
        populatePartData();
    }

    /**
     *
     * @param actionEvent is unused.
     * @throws IOException exceptions are thrown in isValid and is complete methods.
     * calls edit or create new part methods depending on isEdit flag.
     */
    @FXML
    public void onSavePartClick (ActionEvent actionEvent) throws IOException {
        if(isEdit){
            editExistingPart();
        }else{
            createNewPart();
        }

    }

    /**
     *
     * @param actionEvent is unused.
     * @throws IOException
     * Exits add part window, and redirects back to main.
     * does not save current part.
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

        Stage currentStage = (Stage) add_part_cancel_btn.getScene().getWindow();
        currentStage.close();
    }

    /**
     * @throws IOException
     * Creates new part class outsourced or inHouse depending on current selection.
     * Creates new main page and gives it the new inventory object with the new part.
     */
    private void createNewPart() throws IOException{
        boolean isComplete= checkFields();
        boolean isValid =  checkValidInput();

        if(isComplete && isValid){
            String name = add_part_name_field.getText();
            int inv = Integer.parseInt(add_part_inv_field.getText().strip());
            double price = Double.parseDouble(add_part_price_field.getText().strip());
            int max = Integer.parseInt(add_part_max_field.getText().strip());
            int min = Integer.parseInt(add_part_min_field.getText().strip());

            if(outsourced_btn.isSelected()){
                String machineIdOrName = add_part_id_or_company_field.getText();
                Outsourced newPart = new Outsourced(Id, name,  price, inv, min, max, machineIdOrName);
                this.inventory.addPart(newPart);
            }else{
                int machineIdOrName = Integer.parseInt(add_part_id_or_company_field.getText());
                InHouse newPart = new InHouse(Id, name,  price, inv, min,max, machineIdOrName);
                this.inventory.addPart(newPart);
            }

            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("pages/MainPage.fxml"));
            Parent root = mainLoader.load();
            MainController mainController = mainLoader.getController();
            mainController.initialize(this.inventory);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Inventory Management System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            Stage currentStage = (Stage) add_part_save_btn.getScene().getWindow();
            currentStage.close();
        }
    }

    /**
     * @throws IOException
     * updates an existing part using part setter methods.
     * Creates new main page and gives it the inventory with the edited part.
     */
    private void editExistingPart() throws IOException{
        boolean isComplete= checkFields();
        boolean isValid =  checkValidInput();

        if(isComplete && isValid){
            String name = add_part_name_field.getText();
            String machineIdOrName = add_part_id_or_company_field.getText();
            int inv = Integer.parseInt(add_part_inv_field.getText().strip());
            double price = Double.parseDouble(add_part_price_field.getText().strip());
            int max = Integer.parseInt(add_part_max_field.getText().strip());
            int min = Integer.parseInt(add_part_min_field.getText().strip());


            for(int i =0; i < inventory.getAllParts().size(); i++){
                if(inventory.getAllParts().get(i).getId() == Id ){
                    Part newPart;
                    if(outsourced_btn.isSelected()){
                        newPart = new Outsourced(Id,name, price,inv, min, max, machineIdOrName );
                    }else{
                        int machineId = Integer.parseInt(machineIdOrName);
                        newPart = new InHouse(Id,name, price,inv, min, max, machineId );
                    }
                    inventory.updatePart(i,newPart);
                    updateInProduct(newPart);
                }
            }

            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("pages/MainPage.fxml"));
            Parent root = mainLoader.load();
            MainController mainController = mainLoader.getController();
            mainController.initialize(this.inventory);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Inventory Management System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            Stage currentStage = (Stage) add_part_save_btn.getScene().getWindow();
            currentStage.close();

        }

    }

    /**
     *
     * @param part part to update
     * removes the old version of the part in the product and replaces it with a new one.
     * note: this is an O(n^2) operation.
     */
    private void updateInProduct(Part part){
        inventory.getAllProducts().forEach(product -> {
            product.getAllAssociatedParts().forEach(item->{
                if(item.getId() == Id){
                    product.deleteAssociatedPart(part.getId());
                    product.addAssociatedPart(part);
                }
            });
        });
    }

    /**
     * @return true if all fields are filled, else false.
     *
     * Checks that all required fields have been filled.
     * Shows an alert identifying the first found empty field, if necessary.
     */
    private boolean checkFields(){

        TextField[] fields = {add_part_name_field,  add_part_inv_field, add_part_price_field, add_part_max_field,
                add_part_min_field, add_part_id_or_company_field};

        for(TextField field : fields){
            if(field.getText().isEmpty()){

                String[] fieldNameList = field.getId().split("_");
                String fieldName = "Part " + field.getId() == "add_part_company_name_field" ? "Company Name": fieldNameList[fieldNameList.length -2];
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
        TextField[] fieldsToCheck = { add_part_inv_field, add_part_price_field, add_part_max_field,
                add_part_min_field, add_part_id_or_company_field};
        int min = 0;
        int max = 0;
        int inventory = 0;

        for(TextField field : fieldsToCheck){
            switch (field.getId()) {

                case "add_part_inv_field":
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

                case "add_part_price_field":
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

                case "add_part_max_field":
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

                case "add_part_min_field":
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

                case "add_part_id_or_company_field":
                    if(in_house_btn.isSelected()){
                        try{
                            Integer.parseInt(field.getText().strip());
                        }catch(Exception ex){
                            Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                            errorMsg.setHeaderText("Machine Id invalid");
                            errorMsg.setContentText("Machine Id is an integer value.");
                            errorMsg.show();
                            return false;
                        }
                    }else{
                        if(Character.isDigit(field.getText().charAt(0))){
                            Alert intForName = new Alert(Alert.AlertType.CONFIRMATION);
                            intForName.setHeaderText("Company name Conformation");
                            intForName.setContentText("The company name Begins with a digit. \n Do you wish to continue?");
                            intForName.initModality(Modality.APPLICATION_MODAL);
                            intForName.showAndWait();
                            Boolean response = intForName.getResult().getButtonData().isDefaultButton();
                            return response;
                        }
                    }
                    break;

                default:
                    System.out.println("You have made a mistake in part controller check valid input.");
                    return false;

            }

            }


        if(min > max){
            Alert errorMsg = new Alert(Alert.AlertType.ERROR);
            errorMsg.setHeaderText("Invalid Input");
            errorMsg.setContentText("The minimum number of part should be less than the maximum.");
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
     * sets the fields to the part data.
     */
    private void populatePartData(){
        inventory.getAllParts().forEach(item -> {
            if(item.getId() == this.Id){
               add_part_name_field.setText(item.getName());
               add_part_inv_field.setText(String.valueOf(item.getStock()));
               add_part_price_field.setText(String.valueOf(item.getPrice()));
               add_part_max_field.setText(String.valueOf(item.getMax()));
               add_part_min_field.setText(String.valueOf(item.getMin()));
               if(item instanceof InHouse){
                   in_house_btn.setSelected(true);
                   add_part_id_or_company_field.setText( String.valueOf(((InHouse) item).getMachinedId()));
               }else{
                   outsourced_btn.setSelected(true);
                   add_part_id_or_company_field.setText( ((Outsourced) item).getCompanyName());
               }


            }
        });
    }
}
