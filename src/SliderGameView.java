import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SliderGameView extends Pane {

    private Button[][]        puzzleButtons     = new Button[4][4];
    private ListView<String>  puzzleList        = new ListView<String>();
    private String[]          puzzleNames       = {"Pets", "Scenery", "Lego", "Numbers"};
    private Label             thumbnail         = new Label();
    private Button            startStopButton   = new Button("Start");
    private Label             timeLabel         = new Label("Time:");
    private TextField         timeField         = new TextField();

    // Get methods
    public ListView<String> getList()   {return puzzleList; }
    public String[] getPuzzleNames()    {return puzzleNames;}
    public Button[][] getPuzzleButtons(){return puzzleButtons;}
    public Button getStartStopButton()  {return startStopButton;}
    public Label getThumbnail()         {return thumbnail;}
    public TextField getTimeField()     {return timeField;}

    // Constructor
    public SliderGameView(){

        // PUZZLE BOARD LAYOUT
        // Initialize puzzle board layout
        GridPane puzzleLayout = new GridPane();
        puzzleLayout.setHgap(1);
        puzzleLayout.setVgap(1);

        // Add each button to the grid in the appropriate places
        for (int row=0; row<4; row++){
            for (int column = 0; column <4; column ++){
                puzzleButtons[row][column] = new Button();
                puzzleButtons[row][column].setPrefSize(187, 187);
                puzzleButtons[row][column].setPadding(new Insets(0,0,0,0));
                puzzleLayout.add(puzzleButtons[row][column], column, row);
            }
        }

        // Set up the puzzle board layout's location in the main window
        puzzleLayout.relocate(10, 10);

        // RIGHT SIDE LAYOUT
        // Initialize right side layout
        Pane rightSideLayout = new Pane();

        // Set up thumbnail
        thumbnail.setPrefSize(187, 187);
        thumbnail.relocate(0, 0);
        thumbnail.setGraphic
                (new ImageView(new Image(getClass().getResourceAsStream(puzzleNames[0] + "_Thumbnail.png"))));

        // Set up puzzleList below thumbnail
        puzzleList.setItems(FXCollections.observableArrayList(puzzleNames));
        puzzleList.getSelectionModel().select(0);
        puzzleList.setPrefSize(187, 140);
        puzzleList.relocate(0, 197);

        // Set up startStopButton below puzzleList
        startStopButton.setStyle("-fx-base: rgb(0,100,0); -fx-text-fill: rgb(255,255,255);");
        startStopButton.setPrefSize(187, 30);
        startStopButton.relocate(0, 347);

        // Set up timeLabel below startStopButton and to the left
        timeLabel.setPrefSize(60, 30);
        timeLabel.relocate(0, 387);

        // Set up timeField to the right of the timeLabel
        timeField.setText("0:00");
        timeField.setPrefSize(127, 30);
        timeField.relocate(60, 387);

        // Add all of the set ups above and set up the right side layout's location
        // in the main window
        rightSideLayout.getChildren().addAll(thumbnail, puzzleList, startStopButton, timeLabel, timeField);
        rightSideLayout.relocate(771, 10);

        // Add the puzzle board layout and the right side layout to the main
        // window and set its size
        getChildren().addAll(rightSideLayout, puzzleLayout);
        setPrefSize(968, 771);
    }

    public void update(SliderGameModel model){

        // Get and store the model's puzzleImages to avoid calling the get function repeatedly
        String[][] puzzleImages = model.getPuzzleImages();

        // Set each button's graphic to the model's applicable puzzleImages row & column
        for (int row=0; row<4; row++){
            for (int column = 0; column <4; column ++){
                puzzleButtons[row][column].setGraphic
                        (new ImageView(new Image(getClass().
                                getResourceAsStream(puzzleImages[row][column]))));
            }
        }

        // Set the thumbnail to the puzzleList's currently selected index and
        // set the puzzleList's index back afterwards
        int selectedIndex = puzzleList.getSelectionModel().getSelectedIndex();
        for (int i = 0; i<4; i++){
            if (i == selectedIndex){
                thumbnail.setGraphic
                        (new ImageView(new Image(getClass().
                                getResourceAsStream(puzzleNames[i] + "_Thumbnail.png"))));
            }
        }
        puzzleList.getSelectionModel().select(selectedIndex);
    }
}
