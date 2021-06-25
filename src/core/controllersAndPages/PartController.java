package core.controllersAndPages;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.MessageFormat;



/**
 * controller for the parts page.
 */

public class PartController {

    @FXML
    private Label add_part_machine_label;

    @FXML
    private TextField add_part_name_field,add_part_inv_field,add_part_price_field,
            add_part_max_field, add_part_min_field, add_part_id_or_company_field;

    @FXML
    private RadioButton in_house_btn,outsourced_btn;

    @FXML
    public void initialize(){
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




    @FXML
    public void onSavePartClick(ActionEvent actionEvent) {

        boolean isComplete= checkFields();

        if(isComplete){
            if(checkValidInput()){

            }
        }

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

        for(TextField field : fieldsToCheck){
            switch (field.getId()) {

                case "add_part_inv_field":
                    try {
                        Integer.parseInt(field.getText().strip());
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
                        Float.parseFloat(field.getText().strip());
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
                        int val =Integer.parseInt(field.getText().strip());
                        max = val;
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
                       int val = Integer.parseInt(field.getText().strip());
                       min = val;
                    }catch(Exception ex){
                        Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                        errorMsg.setHeaderText("Minimum Value Invalid");
                        errorMsg.setContentText("Please enter an integer value for minimum inventory.");
                        errorMsg.show();
                        return false;
                    }
                    break;

                case "add_part_id_or_company_field":
                    if(outsourced_btn.isSelected()){
                        // working here
                    }else{
                        try{
                            Integer.parseInt(field.getText().strip());
                        }catch(Exception ex){
                            Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                            errorMsg.setHeaderText("Minimum Value Invalid");
                            errorMsg.setContentText("Please enter an integer value for minimum inventory.");
                            errorMsg.show();
                            return false;
                        }
                        break;
                    }


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
        return true;
    }
}
