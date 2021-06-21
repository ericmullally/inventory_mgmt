package core.controllersAndPages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.List;

/**
 * controller for the parts page.
 */
public class PartController {

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
        String[] fields = {add_part_name_field.getText(),add_part_machine_field.getText(), add_part_inv_field.getText(),
                add_part_price_field.getText(),add_part_max_field.getText(), add_part_min_field.getText() };
        int blankFieldposition = -1;

        for(int i = 0; i < fields.length; i++){
            if(fields[i].isBlank()){
                blankFieldposition = i;
                return false;
            }
        }
        return true;

    }
}
