package controllers.Front.Animal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entities.Animal;
import services.ServiceAdoption;
import services.ServiceAnimal;
import services.ServiceBoarding;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
public class Quiz {
    @FXML
    private MenuItem btnanimal;

    @FXML
    private MenuItem btnboarding;

    @FXML
    private MenuItem btndonationm;

    @FXML
    private MenuItem btndonationp;

    @FXML
    private MenuItem btnindexb;

    @FXML
    private MenuItem btnproduct;

    @FXML
    private MenuBar menuBar;

    @FXML
    private VBox quizVBox;

    @FXML
    private ChoiceBox<String> experienceChoiceBox;

    @FXML
    private ChoiceBox<String> dailyCommitmentChoiceBox;

    @FXML
    private ChoiceBox<String> livingSpaceChoiceBox;

    @FXML
    private ChoiceBox<String> budgetChoiceBox;

    @FXML
    private ChoiceBox<String> allergiesChoiceBox;

    @FXML
    private Button submit;

    private final ServiceAnimal animalService;

    public Quiz() {
        animalService = new ServiceAnimal();
    }

    @FXML
    private void submitForm() throws SQLException {
        String experience = experienceChoiceBox.getValue();
        String dailyCommitment = dailyCommitmentChoiceBox.getValue();
        String livingSpace = livingSpaceChoiceBox.getValue();
        String budget = budgetChoiceBox.getValue();
        String allergies = allergiesChoiceBox.getValue();

        String recommendedAnimalType = determineAnimal(experience, dailyCommitment, livingSpace, budget, allergies);

        System.out.println("Recommended animal type: " + recommendedAnimalType);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/Recommendation.fxml"));
            Parent root = loader.load();
            Recommendation recommendationController = loader.getController();
            recommendationController.displayRecommendation(recommendedAnimalType);

            Stage stage = (Stage) submit.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private String determineAnimal(String experience, String dailyCommitment, String livingSpace, String budget, String allergies) throws SQLException {
        List<Animal> availableAnimals = animalService.fetchAvailableAnimals();

        Map<String, Integer> animalOptions = new HashMap<>();
        animalOptions.put("Dog", 0);
        animalOptions.put("Cat", 0);
        animalOptions.put("Fish", 0);
        animalOptions.put("Bird", 0);
        animalOptions.put("Reptile", 0);

        // Apply quiz criteria and update animal options based on available animals
        for (Animal animal : availableAnimals) {
            if (experience.equals("Beginner")) {
                animalOptions.put("Fish", animalOptions.get("Fish") + 5);
                animalOptions.put("Bird", animalOptions.get("Bird") + 2);
            } else if (experience.equals("Intermediate")) {
                animalOptions.put("Dog", animalOptions.get("Dog") + 3);
                animalOptions.put("Fish", animalOptions.get("Fish") + 3);
            } else if (experience.equals("Experienced")) {
                animalOptions.put("Reptile", animalOptions.get("Reptile") + 2);
                animalOptions.put("Cat", animalOptions.get("Cat") + 5);
            }

            if (dailyCommitment.equals("Low (1-2 hours)")) {
                animalOptions.put("Fish", animalOptions.get("Fish") + 5);
                animalOptions.put("Bird", animalOptions.get("Bird") + 3);
                animalOptions.put("Reptile", animalOptions.get("Reptile") + 2);
            } else if (dailyCommitment.equals("Medium (2-4 hours)")) {
                animalOptions.put("Dog", animalOptions.get("Dog") + 3);
                animalOptions.put("Cat", animalOptions.get("Cat") + 3);
                animalOptions.put("Bird", animalOptions.get("Bird") + 2);
                animalOptions.put("Reptile", animalOptions.get("Reptile") + 2);
            } else if (dailyCommitment.equals("High (4+ hours)")) {
                animalOptions.put("Dog", animalOptions.get("Dog") + 5);
                animalOptions.put("Cat", animalOptions.get("Cat") + 5);
            }

            if (livingSpace.equals("Apartment")) {
                animalOptions.put("Fish", animalOptions.get("Fish") + 5);
                animalOptions.put("Bird", animalOptions.get("Bird") + 3);
                animalOptions.put("Reptile", animalOptions.get("Reptile") + 2);
            } else if (livingSpace.equals("House")) {
                animalOptions.put("Dog", animalOptions.get("Dog") + 5);
                animalOptions.put("Cat", animalOptions.get("Cat") + 5);
            }

            if (budget.equals("Low")) {
                animalOptions.put("Fish", animalOptions.get("Fish") + 5);
                animalOptions.put("Bird", animalOptions.get("Bird") + 3);
                animalOptions.put("Reptile", animalOptions.get("Reptile") + 2);
            } else if (budget.equals("Medium")) {
                animalOptions.put("Bird", animalOptions.get("Bird") + 5);
                animalOptions.put("Cat", animalOptions.get("Cat") + 5);
            } else if (budget.equals("High")) {
                animalOptions.put("Dog", animalOptions.get("Dog") + 5);
                animalOptions.put("Cat", animalOptions.get("Cat") + 5);
            }

            if (allergies.equals("Yes")) {
                animalOptions.put("Dog", animalOptions.get("Dog") - 10);
                animalOptions.put("Cat", animalOptions.get("Cat") - 10);
            }
        }

        // Find the animal with the highest score
        int maxScore = Integer.MIN_VALUE;
        String recommendedAnimal = "";
        for (Map.Entry<String, Integer> entry : animalOptions.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                recommendedAnimal = entry.getKey();
            }
        }

        return recommendedAnimal;
    }



    @FXML
    public void NavigateToIndexBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/indexFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnindexb.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
    @FXML
    public void NavigateToDisplayAnimal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/DisplayAnimal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnanimal.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToAddBoarding() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Animal/AddBoarding.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnboarding.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Product/DisplayProduct.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnproduct.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationM() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationM/DisplayDonationM.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationm.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }

    @FXML
    public void NavigateToDisplayDonationP() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/DonationP/DisplayDonationP.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btndonationp.getParentPopup().getOwnerWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("United Pets");
        stage.show();
    }
}
