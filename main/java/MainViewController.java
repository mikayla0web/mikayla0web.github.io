package edu.miracosta.cs112.finalproject.finalproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class MainViewController {
    @FXML
    public ImageView mainScreenImage;
    @FXML
    public Button speakerButton;
    @FXML
    private ImageView speakerImageView;
    @FXML
    private Label resultTextField;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label beeInfoRight;
    @FXML
    private Label eventTextField;
    @FXML
    private Label beeInfoLeft;

    private Bee currentBee;
    private final ActionsAndEvents actionsAndEvents = new ActionsAndEvents();
    private String currentEvent;
    private boolean eventHandled = true;

    @FXML
    public void setBee(Bee newBee){
        this.currentBee = newBee;
        beeInfoLeft.setText(newBee.toString());
        beeInfoRight.setText(newBee.stats());
        displayInitialEvent();
    }

    private void displayInitialEvent() {
        if (currentBee != null) {
            eventTextField.setText("Welcome to the hive! \n Learn from your mistakes and adapt. \nClick YES to start working!");
            updateMainScreenImage(currentBee.getRole().toLowerCase() + ".jpg");
        } else {
            eventTextField.setText("No bee selected.");
        }
        resultTextField.setText("");
        yesButton.setDisable(false);
        noButton.setDisable(true);
    }

    private void updateMainScreenImage(String imageName) {
        URL imageURL = Application.class.getResource("/edu/miracosta/cs112/finalproject/finalproject/" + imageName);
        if (imageURL != null) {
            mainScreenImage.setImage(new Image(imageURL.toString()));
        } else {
            System.out.println("Image not found: " + imageName);
            //default or placeholder image
            URL defaultImageURL = Application.class.getResource("/edu/miracosta/cs112/finalproject/finalproject/" + currentBee.getRole().toLowerCase() + ".jpg" );
            if (defaultImageURL != null) {
                mainScreenImage.setImage(new Image(defaultImageURL.toString()));
            }
        }
    }

    private void updateImageBasedOnEvent(String event, String result) {
        if (result.contains("Game over")) {
            String gameOverImageName = "currentBee.getRole().toLowerCase()" + "End.jpeg";
            updateMainScreenImage(gameOverImageName);
            return;
        }

        if (!result.isEmpty()) {
            updateMainScreenImage(currentBee.getRole().toLowerCase() + ".jpg");
            return;
        }

        String imageName = "";
        if (event.contains("flower")) {
            imageName = "flowers.jpg";
        } else if (event.contains("nectar")) {
            imageName = "sneakingHoney.png";
        } else if (event.contains("requested") || event.contains("subjects")) {
            imageName = "uneasySubjects.png";
        } else if (event.contains("bear")) {
            imageName = "bear.jpg";
        } else if (event.contains("cloudy")) {
            imageName = "stormy.jpg";
        } else if (event.contains("larvae") || event.contains("snack")) {
            imageName = "royalJelly.png";
        } else if (event.contains("winter") || event.contains("scarce")) {
            imageName = "snow.jpg";
        } else if (event.contains("declared") || event.contains("beautiful")) {
            imageName = "sunny.jpg";
        } else if(event.contains("rations")){
            imageName = "sadDrone.png";
        } else if (event.contains("drink")) {
            imageName = "drink.jpg";
        }
        else {
            imageName = currentBee.getRole().toLowerCase() + ".jpg";
        }

        updateMainScreenImage(imageName);
        System.out.println("Selected image name: " + imageName);
    }

    @FXML
    protected void yesButtonAction() {
        if (currentBee != null) {
            if (!eventHandled) {
                try {
                    String beeRole = currentBee.getRole().toLowerCase();
                    String result = actionsAndEvents.handleEvent(beeRole, true, currentEvent);

                    if (result != null && !result.isEmpty()) {
                        Platform.runLater(() -> {
                            if (resultTextField != null) {
                                resultTextField.setText(result);
                            }
                        });
                        currentBee.setLastEventResult(result);
                        updateImageBasedOnEvent(currentEvent, result);

                        if (currentBee.hasReachedLifespan() ||result.contains("Game over")) {
                            navigateToEndView();
                        } else {
                            eventHandled = true;
                            noButton.setDisable(true);
                            eventTextField.setText("Press YES to continue.");
                        }
                    } else {
                        resultTextField.setText("No result generated.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (resultTextField != null) {
                        resultTextField.setText("Error handling event: " + e.getMessage());
                    }
                }
            } else {
                currentEvent = actionsAndEvents.generateEvent(currentBee.getRole().toLowerCase());
                if (currentEvent != null && !currentEvent.isEmpty()) {
                    eventTextField.setText(currentEvent);
                    resultTextField.setText("");
                    updateImageBasedOnEvent(currentEvent, "");
                    eventHandled = false;
                    noButton.setDisable(false);
                } else {
                    eventTextField.setText("Failed to generate an event. Try again.");
                }
            }
        }
    }



    @FXML
    protected void noButtonAction() {
        if (currentBee == null) {
            eventTextField.setText("No active event to handle!");
            return;
        }

        if (currentEvent == null || currentEvent.isEmpty()) {
            eventTextField.setText("No active event to handle!");
            return;
        }

        try {
            String beeRole = currentBee.getRole().toLowerCase();

            String result = actionsAndEvents.handleEvent(beeRole, false, currentEvent);

            if (result != null && !result.isEmpty()) {
                Platform.runLater(() -> {
                    if (resultTextField != null) {
                        resultTextField.setText(result);
                    }
                });
                currentBee.setLastEventResult(result);
                updateImageBasedOnEvent(currentEvent, result);

                if (currentBee.hasReachedLifespan() ||result.contains("Game over")) {
                    navigateToEndView();
                } else {
                    eventHandled = true;
                    noButton.setDisable(true);
                    eventTextField.setText("Press YES to continue.");
                }
            } else {
                resultTextField.setText("No result generated.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (resultTextField != null) {
                resultTextField.setText("Error handling event: " + e.getMessage());
            }
        }
    }
    @FXML
    protected void speakerButtonAction(){
        String filePath = "/edu/miracosta/cs112/finalproject/finalproject/bee-flying.mp3";
        URL resourceUrl = getClass().getResource(filePath);
        if (resourceUrl != null) {
            Media media = new Media(resourceUrl.toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } else {
            System.out.println("Media file not found: " + filePath);
        }
    }

    @FXML
    protected void initialize() {
        yesButton.setText("YES");
        noButton.setText("NO");
        displayInitialEvent();
        URL speakerURL = Application.class.getResource("/edu/miracosta/cs112/finalproject/finalproject/soundButton.png");
        if (speakerURL != null) {
            speakerImageView.setImage(new Image(speakerURL.toString()));
        }
    }

    private void navigateToEndView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) yesButton.getScene().getWindow();
            Scene endScene = new Scene(root);
            stage.setScene(endScene);
            stage.show();

            EndViewController endController = loader.getController();
            endController.displayBeeStats(currentBee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}