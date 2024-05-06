package controllers.Back.Appointment;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ImageClassifier {
    public static void main(String[] args) throws IOException {
        // Load the pre-trained model
        MultiLayerNetwork model = ModelLoader.loadModel();

        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Create a button to trigger the file chooser dialog
        JButton chooseImageButton = new JButton("Choose Image");
        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // Preprocess the selected image
                        INDArray image = loadImage(selectedFile.getAbsolutePath());

                        // Perform inference on the image
                        INDArray output = model.output(image);

                        // Print the predicted class
                        int predictedClass = output.argMax(1).getInt(0);
                        System.out.println("Predicted class: " + predictedClass);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Create a simple GUI to display the button
        JFrame frame = new JFrame("Image Classifier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chooseImageButton);
        frame.pack();
        frame.setVisible(true);
    }

    public static INDArray loadImage(String filePath) throws IOException {
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
        INDArray image = loader.asMatrix(new File(filePath));

        ImagePreProcessingScaler scaler = new ImagePreProcessingScaler(0, 1);
        scaler.transform(image);

        // Add a dimension to the image array to make it a batch of 1 image
        image = image.reshape(1, 3, 224, 224);

        return image;
    }
}
