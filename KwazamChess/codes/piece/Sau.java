package piece;
import main.GamePanel;

// The Sau class represents a specialized chess piece with unique movement rules
// Inheritance - Sau inherits properties and behaviors from the Piece superclass

// Written by : Mali, Nelly
public class Sau extends Piece
{
    public Sau(int color, int row, int col){
        super(color, row, col);
        
        // Set the image based on the piece's color
        if(color == GamePanel.BLUE){
            image = getImage("/piece/imagechess/blueSAU");
        }
        else{
            image = getImage("/piece/imagechess/redSAU");
        }
        
    }
    
    // Determines if the Sau piece can move to the specified target position
    @Override
    public boolean canMove(int targetRow, int targetCol){
        if(isWithinBoard(targetRow,targetCol)){
            int rowDiff = Math.abs(targetRow - preRow);
            int colDiff = Math.abs(targetCol - preCol);
            
            if((rowDiff == 1 && colDiff == 0) || // horizontal
               (rowDiff == 0 && colDiff == 1) || // vertical
               (rowDiff == 1 && colDiff == 1)){// diagonal
               
               hittingP = getHittingP(targetRow, targetCol);
               return hittingP == null || hittingP.color != this.color;
            }
        }
        return false;
    }
}
