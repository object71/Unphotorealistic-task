/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.hstamenov.unphotorealistic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author hstamenov
 */
public class HomeController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private ImageView imageView;
    
    private BufferedImage originalImage = null;
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
        try {
            originalImage = ImageIO.read(file);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        imageView.setImage(SwingFXUtils.toFXImage(originalImage, null));
    }
}
