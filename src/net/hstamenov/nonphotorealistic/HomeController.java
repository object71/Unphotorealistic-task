/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.hstamenov.nonphotorealistic;

import com.sun.javafx.css.converters.FontConverter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField selectedText;
    @FXML
    private Slider fontSize;
    @FXML
    private Slider matrixSize;
    
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
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png");
        fileChooser.setSelectedExtensionFilter(extensionFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        try {
            originalImage = ImageIO.read(file);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        imageView.setImage(SwingFXUtils.toFXImage(originalImage, null));
    }
    
    public void generateImage() {
        if(originalImage == null) {
            System.out.println("There is no original image");
            return;
        }
        
        String text = selectedText.getText().toUpperCase();
        
        if(text == null || text.length() == 0) {
            System.out.println("Text was null or empty");
            return;
        }
        
        int textLength = text.length();
        int matrixValue = (int)matrixSize.getValue();
        int fontValue = (int)fontSize.getValue();
        
        int width = (originalImage.getWidth() / matrixValue) * matrixValue;
        int height = (originalImage.getHeight() / matrixValue) * matrixValue;
        
        BufferedImage image = new BufferedImage(width, height, originalImage.getType());
        Graphics graphics = image.getGraphics();
        
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.black);
        
        int currentChar = 0;
        
        for(int n = 0; n < height; n += matrixValue) {
            for(int m = 0; m < width; m += matrixValue) {
                int intensity = intensityValueForBlock(originalImage, m, n, matrixValue);
                int calculatedFont = getValueBasedOnIntensity(intensity, fontValue);
                if(calculatedFont > 2) {
                Font font = new Font("Monospace", Font.PLAIN, getValueBasedOnIntensity(intensity, fontValue));
                    graphics.setFont(font);
                    graphics.drawString(text.substring(currentChar, currentChar + 1), m + (matrixValue / 2), n + (matrixValue / 2));
                }
                currentChar++;
                if(currentChar == textLength) {
                    currentChar = 0;
                }
            }
        }
        
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }
    
    public void saveAs() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png");
        fileChooser.setSelectedExtensionFilter(extensionFilter);
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        BufferedImage image = SwingFXUtils.fromFXImage(imageView.getImage(), null);
        ImageIO.write(image, "png", file);
    }
    
    private static int intensityValueForBlock(BufferedImage image, int m, int n, int matrixValue) {
        int intensity = 0;
        
        for(int x = m; x < m + matrixValue; x++) {
            for(int y = n; y < n + matrixValue; y++) {
                int pixelIntensity = 0;
                int colorsCount = 0;
                
                Color color = new Color(image.getRGB(x, y));

                if(color.getRed() > 0) colorsCount++;                
                if(color.getGreen() > 0) colorsCount++;
                if(color.getBlue() > 0) colorsCount++;

                if(colorsCount > 0) {
                    pixelIntensity = (color.getRed() + color.getGreen() + color.getBlue()) / colorsCount;
                }
                
                intensity += pixelIntensity;
            }
        }
        
        intensity /= (matrixValue * matrixValue);
        
        return intensity;
    }
    
    private static int getValueBasedOnIntensity(int intensity, int size) {
        // 0...1
        double intensityPercent = intensity / 255.0;
        double valueDrop = size * (intensityPercent);
        
        return size - (int)valueDrop;
    }
}
