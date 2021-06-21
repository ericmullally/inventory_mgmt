package core.controllersAndPages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.text.MessageFormat;



/**
 * controller for the parts page.
 */
public class PartController {
    @FXML
    private GridPane add_part_grid;

    @FXML
    private TextField add_part_name_field,add_part_inv_field,add_part_price_field,
            add_part_max_field, add_part_min_field, add_part_machine_field;

    @FXML
    private Button add_part_save_btn, add_part_cancel_btn;


    @FXML
    public void onSavePartClick(ActionEvent actionEvent) {
        boolean isFinished = checkFields();
        System.out.println(isFinished);
    }

    /**
     * @return true if all fields are filled, else false.
     *
     * Checks that all required fields have been filled.
     */
    public boolean checkFields(){
        TextField[] fields = {add_part_name_field, add_part_machine_field, add_part_inv_field,
                add_part_price_field,add_part_max_field, add_part_min_field };

        for(int i = 0; i < fields.length; i++){
            if(fields[i].getText().isEmpty()){

                String[] fieldNameList = fields[i].getId().split("_");
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
}
