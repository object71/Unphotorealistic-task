/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.hstamenov.unphotorealistic;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author hstamenov
 */
public class HomeController implements Initializable {
    @FXML
    private BorderPane root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        //FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png");
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        System.out.println(file.getAbsolutePath());
    }
}
