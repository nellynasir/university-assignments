package piece;
import main.GamePanel;

// The Ram class represents a chess piece with unique movement behavior.
// It moves forward and reverses direction upon reaching the opponent's edge.
// Inheritance - Ram extends the Piece superclass, inheriting its properties and methods.

// State Pattern: The Ram dynamically changes its movement state.

// Written by : Mali, Nelly
public class Ram extends Piece
{
    private boolean isReversing = false; // Tracks whether the Ram is reversing

    public Ram(int color, int row, int col){
        super(color, row, col);

        if(color == GamePanel.BLUE){
            // Set the image based on the piece's color
            image = getImage("/piece/imagechess/blueRAM");
        } else {
            image = getImage("/piece/imagechess/redRAM");
        }

    }

    // Determines if the Ram piece can move to the specified target position.
    public boolean canMove(int targetRow, int targetCol){
        if(isWithinBoard(targetRow, targetCol) && !isSameSquare(targetRow, targetCol)) {
            int moveValue = isReversing ? 1 : -1; // State Pattern: Reverse movement direction if needed
            
            // Check if the target square is directly in front of the Ram
            if(targetRow == preRow + moveValue && targetCol == preCol) {
                hittingP = getHittingP(targetRow, targetCol);
                
                // Can move if the square is empty or has an opponent's piece to "kill"
                if(hittingP == null || hittingP.color != this.color) {
                    return true;
                }
            }
        }
        return false;
    }

    // Updates the position of the Ram piece.
    @Override
    public void updatePosition() {
        super.updatePosition();
        // Reverse direction if reaching the opponent's edge
        if((color == GamePanel.BLUE && row == 0) || (color == GamePanel.RED && row == 7)) {
            isReversing = !isReversing; // Toggle reversing state.
        }
    }
}

