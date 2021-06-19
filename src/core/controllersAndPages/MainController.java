package core.controllersAndPages;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    /**
     ****************************************************************** this section is for the part controls
     */

    /**
     * @param actionEvent
     * @throws IOException
     * Opens the add Parts Page when the add button is clicked,
     * in the parts section of the main page
     */
    public void onAddPartClick(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("pages/AddPart.fxml"));
            Stage partStage = new Stage();
            partStage.setScene(new Scene(root));
            partStage.setTitle("Add Part");
            partStage.show();
    }

    /**
     ****************************************************************** this section is for the product controls
     */

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
        productStage.show();
    }
}
