package piece;
import main.GamePanel;

// The Biz class represents a specialized chess piece that moves in an "L" shape
// Inheritance : Biz inherits behavior and attributes from the Piece superclass.

// Written by : Mali, Nelly
public class Biz extends Piece
{
    public Biz(int color, int row, int col){
        super(color, row, col);
        
        // Set the image based on the piece's color
        if(color == GamePanel.BLUE){
            image = getImage("/piece/imagechess/blueBIZ");
        }
        else{
            image = getImage("/piece/imagechess/redBIZ");
        }
    }
    public boolean canMove(int targetRow, int targetCol){ //3 x 2 3:2 or 2:3
        // Check if the target position is within the board boundaries
        if(isWithinBoard(targetRow,targetCol)){
            int rowDiff = Math.abs(targetRow - preRow);
            int colDiff = Math.abs(targetCol - preCol);
            
            // Valid Biz move: (3 x 2) or (2 x 3) movement
            if ((rowDiff == 2 && colDiff == 3) || (rowDiff == 3 && colDiff == 2)){
                // Check if the target square is valid (e.g., not occupied by a friendly piece)
                if (isValidSquare(targetRow, targetCol)){
                    return true;
                }
            }
        }
        return false;
    }

}
