package controllers.Back.DonationM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class thankYouCardController {

    @FXML
    private Label messageLabel;

    @FXML
    private ImageView gifImageView;
    @FXML
    private MediaView mediaView;
    @FXML
    private MediaPlayer mediaPlayer;


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
        } else {
            // Gérer le cas où l'image n'est pas chargée correctement
            System.out.println("Erreur : Impossible de charger l'image !");
        }
    }

    @FXML
    private void closeDialog() {
        // Fermez la fenêtre lorsque l'utilisateur appuie sur le bouton Fermer
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}

