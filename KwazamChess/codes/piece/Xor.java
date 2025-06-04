package piece;
import main.GamePanel;

// The Xor class represents a chess piece with diagonal movement abilities
// It can move diagonally on the board
// Inheritance - Xor extends the Piece superclass, inheriting its properties and behaviors

// State Pattern: The Xor tracks its state through the moveCount.
// When moveCount reaches 2, the state transitions back to a Tor piece.
// Decorator Pattern: The Xor dynamically decorates itself with Tor behavior,
// switching from diagonal movement to straight-line movement when transformed.

// Written by : Mali, Nelly
public class Xor extends Piece
{
    public Xor(int color, int row, int col){
        super(color, row, col);
        // Set the image based on the piece's color
        if(color == GamePanel.BLUE){
            image = getImage("/piece/imagechess/blueXOR");
        }
        else{
            image = getImage("/piece/imagechess/redXOR");
        }
        
    }
    
    // Updates the position of the Xor piece.
    @Override
    public void updatePosition(){
        super.updatePosition();
        incrementMoveCount();
        // Transform the piece to Tor if it has moved twice
        if(moveCount == 2){
            transformToTOR();
        }
    }
    
    // Transforms the Xor piece into a Tor piece
    private void transformToTOR(){
        Piece newPiece = new Tor(this.color, this.row, this.col);
        GamePanel.simPieces.set(getIndex(), newPiece);
    }
    
    // Determines if the Xor piece can move to the specified target position.
    public boolean canMove(int targetRow, int targetCol){
        // Ensure the target position is within the board and not the same as the current position
        if(isWithinBoard(targetRow,targetCol) && isSameSquare(targetRow,targetCol)== false){
            // The piece can move if the row and column difference are equal (diagonal movement)
            if(Math.abs(targetRow - preRow) == Math.abs(targetCol - preCol)){
                // Check if the target square is valid and the diagonal path is clear
                if(isValidSquare(targetRow, targetCol) && pieceIsOnDiagonalLine(targetRow, targetCol) == false){
                    return true;
                }
            }
        }
        return false;
    }
}
