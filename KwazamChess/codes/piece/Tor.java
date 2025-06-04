package piece;
import main.GamePanel;

// The Tor class represents a specialized chess piece with linear movement
// It moves in straight lines (either horizontally or vertically)
// Inheritance - Tor extends the Piece superclass, inheriting its properties and behavior

// Decorator Pattern: The Tor dynamically decorates itself with Xor behavior,
// enabling diagonal movement capabilities when transformed.

// Written by : Mali, Nelly
public class Tor extends Piece
{
    public Tor(int color, int row, int col){
        super(color, row, col);
        
        // Set the image based on the piece's color
        if(color == GamePanel.BLUE){
            image = getImage("/piece/imagechess/blueTOR");
        }
        else{
            image = getImage("/piece/imagechess/redTOR");
        }

    }
    
    // Updates the position of the Tor piece
    @Override
    public void updatePosition(){
        super.updatePosition();
        incrementMoveCount();
        // Transform the piece to XOR if it has moved twice
        if(moveCount == 2){
            transformToXOR();
        }
    }
    
    // Transforms the Tor piece into an XOR piece
    private void transformToXOR(){
        Piece newPiece = new Xor(this.color, this.row, this.col);
        GamePanel.simPieces.set(getIndex(), newPiece);
    }
    
    // Decorator Pattern: The Tor dynamically decorates itself with Xor behavior
    // Determines if the Tor piece can move to the specified target position.
    public boolean canMove(int targetRow, int targetCol){
        // Ensure the target position is within the board and not the same as the current position
        if (isWithinBoard(targetRow,targetCol)&& isSameSquare(targetRow,targetCol)== false){
            //Tor can move as long as either its row and coll is the same 
            if(targetRow == preRow || targetCol == preCol){
                // Check if the target square is valid and the path is clear
                if(isValidSquare(targetRow,targetCol) && (pieceIsOnStraightLine(targetRow,targetCol)== false)){
                    return true;
                }
            }
        }
        return false;
    }

}
