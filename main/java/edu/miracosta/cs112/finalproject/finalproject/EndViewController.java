package edu.miracosta.cs112.finalproject.finalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class EndViewController {

    @FXML
    private ImageView endScreenImage;
    @FXML
    private Label titleTextField;
    @FXML
    private Label summaryTextArea;
    @FXML
    private Button restartButton;
    @FXML
    private ImageView beeImageView;

    public void displayBeeStats(Bee bee) {
        // Load the general background image for the end screen
        URL generalEndScreenURL = Application.class.getResource("/edu/miracosta/cs112/finalproject/finalproject/end_screen.png");
        if (generalEndScreenURL != null) {
            endScreenImage.setImage(new Image(generalEndScreenURL.toString()));
        } else {
            System.out.println("Failed to load general end screen image: end_screen.png");
        }

        String beeTypeLower = bee.getType().toLowerCase();
        String outcomeMessage = determineDeathReason(bee);

        // Define the "survival" message
        String survivalIndicatorMessage = "The bee's journey came to an end, leaving behind a legacy in the hive.";
        boolean survived = outcomeMessage.equals(survivalIndicatorMessage);

        String specificBeeImageName;
        String summaryText;

        if (survived) {
            titleTextField.setText( "Congratulations " + bee.getName().toLowerCase() + ", you adapted and survived!");
            specificBeeImageName = beeTypeLower + "Survived.png";
            summaryText = "This " + bee.getName().toLowerCase() + " has successfully completed its journey and left a buzzing legacy!";
        } else {
            titleTextField.setText("Your life as a " + bee.getName().toLowerCase() + " has ended.");
            specificBeeImageName = beeTypeLower + "End.jpeg";

            if (bee.getLastEventResult() != null && !bee.getLastEventResult().isEmpty()) {
                summaryText = bee.getLastEventResult() + "\n\nLearn from your mistakes and try again!";
            } else {
                // Fallback if lastEventResult is null or empty
                summaryText = "The bee's journey has ended unexpectedly.\n\nLearn from your mistakes and try again!";
            }
        }

        // Load the specific image for the bee (either survived or end)
        URL beeImageURL = Application.class.getResource("/edu/miracosta/cs112/finalproject/finalproject/" + specificBeeImageName);

        if (beeImageURL != null) {
            beeImageView.setImage(new Image(beeImageURL.toString()));
            System.out.println("Loaded bee image: " + specificBeeImageName);
        } else {
            System.out.println("Failed to load bee image: " + specificBeeImageName);
            // Fallback image logic
            if (survived) { // If survived image failed, try to load corresponding end image
                String fallbackImageName = beeTypeLower + "End.jpeg";
                URL fallbackImageURL = Application.class.getResource("/edu/miracosta/cs112/finalproject/finalproject/" + fallbackImageName);
                if (fallbackImageURL != null) {
                    beeImageView.setImage(new Image(fallbackImageURL.toString()));
                    System.out.println("Loaded fallback bee image: " + fallbackImageName);
                } else {
                    System.out.println("Failed to load any specific image for bee type: " + beeTypeLower);
                    beeImageView.setImage(null); // Clear image if no fallback found
                }
            } else { // If end image itself failed
                beeImageView.setImage(null); // Clear image
            }
        }

        summaryTextArea.setText(summaryText); // Set the determined summary text
        restartButton.setText("Restart Game");
    }

    private String determineDeathReason(Bee bee) {
        if (bee.getTimeAlive() >= 7) {
            return "The bee passed away from old age.";
        }

        String lastEvent = bee.getLastEventResult();
        if (lastEvent != null && (lastEvent.contains("Game over") || lastEvent.contains("old age"))) {
            switch (bee.getType().toLowerCase()) {
                case "worker":
                    if (lastEvent.contains("fly swatter")) return "The bee's curiosity led to an unfortunate encounter with a fly swatter.";
                    if (lastEvent.contains("stinger")) return "The bee heroically defended the hive, sacrificing its life by stinging a bear.";
                    if (lastEvent.contains("raindrop")) return "The bee was caught in a sudden rainstorm and couldn't survive.";
                    break;
                case "drone":
                    if (lastEvent.contains("treason")) return "The bee was publicly executed for attempting to steal nectar.";
                    if (lastEvent.contains("tricycle")) return "The bee's adventure outside the hive was cut short by an unexpected vehicle.";
                    if (lastEvent.contains("queen")) return "The bee was publicly executed for disrespecting the queen.";
                    break;
                case "queen":
                    if (lastEvent.contains("poisoned")) return "The queen was betrayed by her own servant.";
                    if (lastEvent.contains("revolt")) return "The queen lost the trust of her subjects and was overthrown.";
                    break;
            }
            return "The bee's journey came to an untimely end during a critical moment.";
        }

        return "The bee's journey came to an end, leaving behind a legacy in the hive.";
    }

    @FXML
    private void restartGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) restartButton.getScene().getWindow();
            Scene startScene = new Scene(root);
            stage.setScene(startScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}