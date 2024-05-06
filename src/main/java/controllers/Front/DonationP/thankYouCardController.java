package controllers.Front.DonationP;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.Request;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class thankYouCardController {

    @FXML
    private Label messageLabel;

    @FXML
    private ImageView gifImageView;



    public void setMessage(String message) {
        messageLabel.setText(message);
    }




    /*private void displayThankYouDialog(String message, Media media) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationM/ThankYouDialog.fxml"));
            Parent root = loader.load();

            thankYouCardController controller = loader.getController();
            controller.setMessage(message);

            // Charger la vidéo
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Thank You!");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MediaView getMediaView() {
        return mediaView;
    }
    private Media loadThankYouVideo() {
        // Spécifiez le chemin d'accès à votre fichier vidéo ou l'URL si la vidéo est en ligne
        String videoPath = "@../../../../resources/images/thankYou.mp4";
        return new Media(videoPath);
    }*/
    public void setImage(Image image) {
        if (image != null) {
            gifImageView.setImage(image);
            Group root= new Group(gifImageView);
           // return root;
        } else {
            // Gérer le cas où l'image n'est pas chargée correctement
            System.out.println("Erreur : Impossible de charger l'image !");
            //return null;
        }
    }
    public void displayThankYouDialog(String message, String gifPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/DonationM/ThankYouDialog.fxml"));
            Parent root = loader.load();

            thankYouCardController controller = loader.getController();
            controller.setMessage(message);

            File gifFile = new File(gifPath);
            if (gifFile.exists()) {
                System.out.println("File exists: " + gifFile.getAbsolutePath());
                Image gifImage = new Image(new File(gifPath).toURI().toString(), true);
                gifImage.errorProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        System.err.println("Failed to load the image: " + gifImage.getException());
                    }
                });
                controller.setImage(gifImage);
            } else {
                System.out.println("File does not exist: " + gifPath);
                // Load a default image or handle the error
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Thank You!");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String loadThankYouGif() {
        OkHttpClient client = new OkHttpClient();
        String apiKey = "BATFVvFWfiErz5jFuCVDfM3EkQfLW3Rk";
        String keyword = "thank you";
        String gifUrl = null;

        Request request = new Request.Builder()
                .url("https://api.giphy.com/v1/gifs/random?api_key=" + apiKey + "&tag=" + keyword)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                JSONObject data = jsonObject.getJSONObject("data");
                 gifUrl = data.getJSONObject("images").getJSONObject("original").getString("url").toString();

                // Charger le GIF à partir de l'URL

            } else {
                System.err.println("Erreur lors de la requête : " + response.code() + " " + response.message());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }

        return gifUrl;
    }

    public String generateThankYouMessage(String donorName) {
        return "Thank you, " + donorName + ", for your generous donation! We appreciate your support.";
    }

    @FXML
    private void closeDialog() {
        // Fermez la fenêtre lorsque l'utilisateur appuie sur le bouton Fermer
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}

