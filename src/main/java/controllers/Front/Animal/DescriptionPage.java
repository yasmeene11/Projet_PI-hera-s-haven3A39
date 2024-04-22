package controllers.Front.Animal;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import entities.Animal;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ServiceAnimal;

import java.io.InputStream;
import java.sql.SQLException;

public class DescriptionPage {

    @FXML
    private Label animalNameLabel;

    @FXML
    private Label animalAgeLabel;

    @FXML
    private Label animalBreedLabel;

    @FXML
    private Label animalStatusLabel;

    @FXML
    private Label animalDescriptionLabel;

    @FXML
    private Label animalImageLabel;

    private final ServiceAnimal animalService;

    public DescriptionPage() {
        this.animalService = new ServiceAnimal();
    }

    public void initData(int animalId) {
        try {
            // Fetch the animal from the database using its ID
            Animal animal = animalService.fetchAnimalById(animalId);

            // Set the animal information in the labels and image
            if (animal != null) {
                animalNameLabel.setText("Animal Name: " + animal.getAnimal_Name());
                animalAgeLabel.setText("Animal Age: " + animal.getAge());
                animalBreedLabel.setText("Animal Breed: " + animal.getAnimal_Breed());
                animalStatusLabel.setText("Animal Status: " + animal.getAnimal_Status());
                animalDescriptionLabel.setText("Animal Description: " + animal.getAnimal_Description());


                ImageView imageView = new ImageView();

                // Check if the image file exists in the animal_images directory
                InputStream imageStream = getClass().getResourceAsStream("/animal_images/" + animal.getAnimal_Image());
                if (imageStream != null) {
                    imageView.setImage(new Image(imageStream)); // Load image from animal_images directory
                } else {
                    // Load image directly from its original location
                    String originalImagePath = "file:///" + animal.getAnimal_Image();
                    imageView.setImage(new Image(originalImagePath));
                }

                imageView.setFitWidth(200); // Set image width
                imageView.setFitHeight(200); // Set image height

                // Add the ImageView to the VBox
                ((VBox) animalNameLabel.getParent()).getChildren().add(imageView);
            } else {
                // Handle case when animal with given ID is not found
                animalNameLabel.setText("Animal Not Found");
                animalAgeLabel.setText("");
                animalBreedLabel.setText("");
                animalStatusLabel.setText("");
                animalDescriptionLabel.setText("");
                animalImageLabel.setText("");
            }
        } catch (SQLException e) {
            // Handle database error
            e.printStackTrace();
            // Display error message or handle as needed
        }
    }

}
