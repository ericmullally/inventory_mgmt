
package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("controllersAndPages/pages/MainPage.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    /**
     *
     * @param args
     * javadoc location: inventory_mgmt/javadoc
     *
     * RUNTIME ERROR: inventory_mgmt/src/core/controllersAndPages/PartController/ editExistingPart:
     * I tried to use a for each loop to go through and match the id of the part being edited.
     * then update the part. However I could not get the part to switch properly from outsourced to inHouse.
     * So I instead changed it to a for loop and used the inventory class updatePart function. It actually cleaned up
     * the code quite a bit. As well as now being able to properly edit the part.
     *
     * IMPROVEMENT: The obvious improvement would be to use a database to house all the parts and products so that the
     * information persists. A less obvious one would be to have a system to monitor the parts and products inventory
     * level. and have it alert you when you were approaching the minimum quantity.
     *
     */
    public static void main(String[] args) {
        launch(args);
    }

}
