public class SliderGameModel {

    private String[][]  puzzleImages        = new String[4][4];
    private String[][]  correctPuzzleImages = new String[4][4];
    private int         randomRow;
    private int         randomColumn;
    private String      missingPuzzlePiece;

    // Get methods
    public String[][] getPuzzleImages()         {return puzzleImages;}

    // Constructor
    public SliderGameModel (String whichPuzzle){

        // Populate puzzleImages and correctPuzzleImage with blank tiles
        // if the input is blank
        if (whichPuzzle.equals("Blank")){
            for (int row=0; row<4; row++){
                for (int column = 0; column <4; column ++){
                    puzzleImages[row][column] = "BLANK.png";
                    correctPuzzleImages[row][column] = "BLANK.png";
                }
            }
        }

        //MostPop = {MostPopular, SecondMost, ThirdMost};

        // Otherwise, populate puzzleImages and correctPuzzleImages with applicable
        // puzzle tiles
        else {
            for (int row=0; row<4; row++){
                for (int column = 0; column <4; column ++){
                    puzzleImages[row][column] = whichPuzzle + "_" + row + column + ".png";
                    correctPuzzleImages[row][column] = whichPuzzle + "_" + row + column + ".png";
                }
            }
        }

        // Choose a random puzzle tile to be BLANK.png and store the missing piece
        randomRow = (int)(Math.random()*4);
        randomColumn = (int)(Math.random()*4);
        missingPuzzlePiece = puzzleImages[randomRow][randomColumn];
        puzzleImages[randomRow][randomColumn] = "BLANK.png";

        // Randomly shuffle around the puzzle pieces thoroughly
        for (int i=0; i<5000; i++){
            swapImage((int)(Math.random()*4), (int)(Math.random()*4));
        }
    }

    public void swapImage(int row, int column){

        // Check to make sure tile being looked at is not the blank tile itself before
        // executing checking above, below, left, and right of the tile
        if (!puzzleImages[row][column].equals("BLANK.png")){

            // Checking above, as long as it's not out of bounds
            if ((row -1) >=0)
                checkAndSwapIfBlank(row-1, column, row, column);

            // Checking below, as long as it's not out of bounds
            if ((row+1) <= 3)
                checkAndSwapIfBlank(row+1, column, row, column);

            // Checking to the left, as long as it's not out of bounds
            if ((column -1) >= 0)
                checkAndSwapIfBlank(row, column-1, row, column);

            // Checking to the right, as long as it's not out of bounds
            if ((column +1) <= 3)
                checkAndSwapIfBlank(row, column+1, row, column);
        }
    }

    private void checkAndSwapIfBlank(int checkRow, int checkColumn, int rowToSwap, int colToSwap){

        // Swap puzzle pieces if the place being checked is the blank tile
        if (puzzleImages[checkRow][checkColumn].equals("BLANK.png")){
            String originalImage = puzzleImages[rowToSwap][colToSwap];
            puzzleImages[rowToSwap][colToSwap] = "BLANK.png";
            puzzleImages[checkRow][checkColumn] = originalImage;
        }
    }

    public Boolean checkForWin(String[][] puzzleImages){

        Boolean isThereAWin = false;
        Integer winCounter = 0;

        // Compare the current puzzleImages to the correctPuzzleImages
        // and count the number of pieces that match
        for (int row=0; row<4; row++){
            for (int column = 0; column <4; column ++){
                if (puzzleImages[row][column].equals(correctPuzzleImages[row][column])){
                    winCounter ++;
                }
            }
        }

        // If all pieces match (except for the blank tile, of course), winCounter
        // will be 15 - win condition gets set to true and missingPuzzlePiece gets
        // filled in if this is the case
        if (winCounter == 15){
            isThereAWin = true;
            puzzleImages[randomRow][randomColumn] = missingPuzzlePiece;
        }

        return isThereAWin;
    }

}
