import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SliderGameController extends Application {

    private SliderGameView  view;
    private SliderGameModel model;
    private Timeline        updateTimer;
    private int             totalSeconds, minutesToDisplay, secondsToDisplay;

    public void start(Stage primaryStage) {

        // Set up the view to be displayed on the sliderGameWindow pane
        Pane sliderGameWindow = new Pane();
        view = new SliderGameView();
        sliderGameWindow.getChildren().addAll(view);

        // Initialize the model to be populated with the blank puzzle tiles and
        // update the display
        model = new SliderGameModel("Blank");
        view.update(model);

        // Set up the timer, track the totalSeconds lapsed, and have the time
        // update to the view's timeField each second that passes
        totalSeconds = 0;
        updateTimer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                totalSeconds++;
                secondsToDisplay = totalSeconds % 60;
                minutesToDisplay = totalSeconds / 60;
                view.getTimeField().setText(minutesToDisplay + ":" + String.format("%02d", secondsToDisplay));
            }
        }));
        updateTimer.setCycleCount(Timeline.INDEFINITE);

        // Set up the window and display it
        primaryStage.setTitle("Slider Puzzle Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(sliderGameWindow));
        primaryStage.show();

        // EVENT HANDLERS
        // Handle When user clicks entries in the view's puzzle list
        view.getList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                handleListSelection();
            }
        });

        // Handle when user clicks the view's startStopButton
        view.getStartStopButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleStartStopPress();
            }
        });

        // Set up each button's event handler to handle when user
        // clicks on any of the view's puzzleButtons
        Button[][] puzzleButtons = view.getPuzzleButtons();
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                puzzleButtons[row][column].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int row = 0; row < 4; row++) {
                            for (int column = 0; column < 4; column++) {
                                if (event.getSource() == puzzleButtons[row][column]){
                                    handlePuzzleButtonPress(row, column);
                                }
                            }
                        }
                    }
                });
            }
        }

    }

        private void handleListSelection () {
            // View's update function updates the thumbnail accordingly, depending on which
            // item in the view's puzzleList is selected
            view.update(model);
        }

        private void handleStartStopPress () {

            // Handling if the button is currently showing "Start"
            if (view.getStartStopButton().getText().equals("Start")) {
                // Set up the selected puzzle
                int selectedIndex = view.getList().getSelectionModel().getSelectedIndex();

                // As long as something is selected, update the view's puzzleButtons according to
                // which puzzle has been selected
                if (selectedIndex >= 0) {
                    String selectedPuzzle = view.getPuzzleNames()[selectedIndex];
                    model = new SliderGameModel(selectedPuzzle);
                    view.update(model);
                }

                // Switch the Start button to Stop
                view.getStartStopButton().setText("Stop");
                view.getStartStopButton().setStyle("-fx-base: rgb(139, 0,0);");

                // Disable the thumbnail, ListView, and start the timer
                view.getThumbnail().setDisable(true);
                view.getList().setDisable(true);
                updateTimer.play();
            }

            // Handling if the button is currently showing "Stop"
            else {

                // Set the puzzle tiles to be blank and update the view accordingly
                model = new SliderGameModel("Blank");
                view.update(model);

                // Switch the Stop button to Start
                view.getStartStopButton().setText("Start");
                view.getStartStopButton().setStyle("-fx-base: rgb(0,100,0);");

                // Enable the thumbnail, ListView, and stop the timer
                view.getThumbnail().setDisable(false);
                view.getList().setDisable(false);
                updateTimer.stop();
            }
        }

        private void handlePuzzleButtonPress(int row, int column){

            // For the clicked puzzleButton, use model's swap method to try to switch it
            model.swapImage(row, column);

            // Check if the user has 'won' by arranging all pieces properly
            if (model.checkForWin(model.getPuzzleImages())){
                // If user has won, stop timer, enable thumbnail, set stop button to start button
                // and disable all of the puzzleButtons
                updateTimer.stop();
                view.getThumbnail().setDisable(false);
                view.getStartStopButton().setText("Start");
                view.getStartStopButton().setStyle("-fx-base: rgb(0,100,0);");
                for (int rows=0; rows<4; rows++){
                    for (int columns = 0; columns <4; columns ++){
                        view.getPuzzleButtons()[rows][columns].setDisable(true);
                    }
                }
            }

            // Then update the model
            view.update(model);
        }
    }

