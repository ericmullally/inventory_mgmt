package core.controllersAndPages;

import core.classes.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.text.MessageFormat;



/**
 * controller for the parts page.
 */
public class PartController {

    @FXML
    private TextField add_part_name_field,add_part_inv_field,add_part_price_field,
            add_part_max_field, add_part_min_field, add_part_machine_field;

    @FXML
    public void onSavePartClick(ActionEvent actionEvent) {

        boolean isComplete= checkFields();
        if(isComplete){
            if(checkValidInput()){
                //               Part newPart =  new Part(, );
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
        TextField[] fields = {add_part_name_field,  add_part_inv_field, add_part_price_field,
                add_part_machine_field, add_part_max_field, add_part_min_field };

        for(TextField field : fields){
            if(field.getText().isEmpty()){

                String[] fieldNameList = field.getId().split("_");
                String fieldName = "Part " + fieldNameList[fieldNameList.length -2];
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
        TextField[] fieldsToCheck = { add_part_inv_field, add_part_price_field, add_part_machine_field,
                add_part_max_field, add_part_min_field };

        for(TextField field : fieldsToCheck){
            if(field == add_part_price_field){
                try {
                    Float.parseFloat(add_part_price_field.getText().strip());
                }catch (Exception ex){
                    Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                    errorMsg.setHeaderText("Price Invalid");
                    errorMsg.setContentText("Please enter a valid price.");
                    errorMsg.show();
                    return false;
                }
            }else{
                try {
                    Integer.parseInt(field.getText().strip());
                }catch(Exception ex){
                    String[] fieldNameList = field.getId().split("_");
                    String fieldName = fieldNameList[fieldNameList.length -2];
                    String information = MessageFormat.format("Part {0} requires a number value.", fieldName);

                    Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                    errorMsg.setHeaderText("Invalid Input");
                    errorMsg.setContentText(information);
                    errorMsg.show();
                    return false;
                }
            }
        }
        return true;
    }
}
