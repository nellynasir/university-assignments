package piece;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import main.Board;
import main.GamePanel;

// The Piece class represents a single chess piece, its attributes (position, color), 
// and its behavior (movement, flipping, rendering). This class follows an object-oriented 
// approach for managing individual pieces on a chessboard, and it contains logic for 
// handling piece movements, collision detection, and rendering.

// Design Pattern : Template. Defines a general canMove method structure, allowing subclasses to implement specific rules
// Design Pattern : Factory. Subclasses represent specific piece types created based on the game state

public class Piece {

    // Instance variables representing piece attributes
    public BufferedImage image; // Image of the piece 
    public int x, y, row, col, preRow, preCol, color; // Position (x, y) and state info (row, col, color)
    public static int currentColor; // Current player's color (0 or 1)
    public Piece hittingP; // A piece being hit by the current piece
    public boolean moved; // Flag to track if the piece has moved
    public boolean flipped = false;  // Flag to track if the piece is flipped
    public int moveCount = 0; // Count of moves made by the piece ; for Xor & Tor

    // Method to increment the move count for the piece for Xor & Tor
    // Written by : Mali
    public void incrementMoveCount(){
        moveCount++;
    }
    
    // Constructor to initialize a piece with its color and initial position (row, col)
    // Written by : Mali
    public Piece(int color, int row, int col){
        this.color = color;
        this.row = row;
        this.col =col;
        x = getX(col);
        y = getY(row);
        preRow = row;
        preCol = col;
    }
    
    // All Getters Methods Written by : Mali
    public BufferedImage getImage(String imagePath){
        BufferedImage image =null;
            try{
                image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
                if (image == null) {
                    System.out.println("Failed to load image: " + imagePath);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return image;
    }

    public int getColor() {
        return color;
    }

    public static void setCurrentColor(int color) {
        currentColor = color; 
    }
     
    public int getX(int col){
        return col * Board.SQUARE_SIZE; // Convert column to x-coordinate
    }

    public int getY(int row){
        return row * Board.SQUARE_SIZE; // Convert row to y-coordinate
    }
    
    public int getCol(int x){
        return(x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE; // Convert x-coordinate to column
    }
    
    public int getRow(int y){
        return(y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE; // Convert y-coordinate to row
    }
    
    // Get the index of the piece in the GamePanel's list of pieces
    public int getIndex(){
        for(int index = 0; index < GamePanel.simPieces.size(); index++){
            if(GamePanel.simPieces.get(index) == this){
                return index;
            }
        }
        return 0;
    }
    
    // Method to update the piece's position after a move
    // Written by : Nelly
    public void updatePosition(){
        y = getY(row);
        x = getX(col);
        preCol = getCol(x);
        preRow = getRow(y);
        moved = true;
    }
    
    // Method to reset the piece's position to its previous state
    // Written by : Nelly
    public void resetPosition(){
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }

    // Static method to flip all pieces (swap positions and update the color)
    // Written by : Sara
    public static void flipPieces(ArrayList<Piece> pieces) {
        currentColor = (currentColor == 0) ? 1 : 0;    // Toggle currentColor between 0 and 1

        for (Piece piece : pieces) {
            // Flip row and column positions
            piece.row = 7 - piece.row;
            piece.col = 4 - piece.col;
            // Update x and y positions accordingly
            piece.x = piece.getX(piece.col);
            piece.y = piece.getY(piece.row);
            // Flip previous positions as well
            piece.preRow = 7 - piece.preRow;
            piece.preCol = 4 - piece.preCol;
        }
    }
    
    // Method to check if the piece can move to a specific target position
    // Written by : Nelly
    public boolean canMove(int targetRow, int targetCol){
        return false;
    }
    
    // Method to check if the target position is within the bounds of the board
    // Written by : Nelly
    public boolean isWithinBoard(int targetRow, int targetCol){
        if(targetCol >= 0 && targetCol <= 4 && targetRow >= 0 && targetRow <= 7){
            return true;
        }
        return false;
    }
    
    // Method to check if the target position is the same as the current position
    // Written by : Nelly
    public boolean isSameSquare(int targetRow, int targetCol){
        if(targetCol == preCol && targetRow == preRow){
            return true;
        }
        return false;
    }
    
    // Method to find and return the piece occupying the target position
    // Written by : Nelly
    public Piece getHittingP(int targetRow, int targetCol){
        for(Piece piece : GamePanel.simPieces){
            if(piece.col == targetCol && piece.row == targetRow && piece != this){
                return piece;
            }
        }
        return null;
    }
    
    // Method to check if the target position is valid (not occupied by a same-color piece)
    // Written by : Nelly
    public boolean isValidSquare(int targetRow, int targetCol){
        hittingP = getHittingP(targetRow,targetCol);
        if (hittingP == null){
            return true;
        }
        else{
            if(hittingP.color != this.color){
                return true;
            }
            else{
                hittingP = null;
            }
        }
        return false;
    }
    
    // Method to check if the piece moves in a straight line (no obstacles in the way)
    // Written by : Mali
    public boolean pieceIsOnStraightLine(int targetRow, int targetCol){
        //When this pieces is moving to the left
        for (int c= preCol-1; c > targetCol; c-- ){
            for (Piece piece : GamePanel.simPieces){
                if (piece.row == targetRow && piece.col == c){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //When this pieces is moving to the right
        for (int c= preCol+1; c < targetCol; c++ ){
            for (Piece piece : GamePanel.simPieces){
                if (piece.row == targetRow && piece.col == c){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //When this pieces is moving to the up
        for (int r= preRow-1; r > targetRow; r-- ){
            for (Piece piece : GamePanel.simPieces){
                if (piece.row == r && piece.col == targetCol){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //When this pieces is moving to the down
        for (int r= preRow+1; r < targetRow; r++ ){
            for (Piece piece : GamePanel.simPieces){
                if (piece.row == r && piece.col == targetCol){
                    hittingP = piece;
                    return true;
                }
            }
        }
        return false;
    }
    
    // Method to check if the piece moves in a diagonal line (no obstacles in the way)
    // Written by : Mali
    public boolean pieceIsOnDiagonalLine(int targetRow, int targetCol){
        if(targetRow < preRow){
        //Up left
        for(int c = preCol-1; c > targetCol; c--){
            int diff =Math.abs(c - preCol);
            for(Piece piece : GamePanel.simPieces){
                if(piece.row == preRow - diff && piece.col == c){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //Up right
        for(int c = preCol+1; c < targetCol; c++){
            int diff =Math.abs(c - preCol);
            for(Piece piece : GamePanel.simPieces){
                if(piece.row == preRow - diff && piece.col == c){
                    hittingP = piece;
                    return true;
                }
            }
        }
        }
        if(targetRow > preRow){
        //Down left
        for(int c = preCol-1; c > targetCol; c--){
            int diff =Math.abs(c - preCol);
            for(Piece piece : GamePanel.simPieces){
                if(piece.row == preRow + diff && piece.col == c){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //Down right
        for(int c = preCol+1; c < targetCol; c++){
            int diff =Math.abs(c - preCol);
            for(Piece piece : GamePanel.simPieces){
                if(piece.row == preRow + diff && piece.col == c){
                    hittingP = piece;
                    return true;
                }
            }
        }
        }
        return false;
    }
    
    // Method to flip the piece's image vertically
    // Written by : Sara
    public void flipImage() {
        // Flip the image vertically
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));  // Translate to prevent the image from flipping out of bounds
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);  // Perform the flip
    }

    // Method to render the piece on the board (flip the image if necessary)
    // Written by : Mali, Sara
    public void draw(Graphics2D g2) {
        // System.out.println("Drawing piece at (" + x + ", " + y + ")");
        // System.out.println("Current Color: " + currentColor);

        // Flip when currentColor changes and update the flipped flag accordingly
        if ((currentColor == 1 && !flipped) || (currentColor == 0 && flipped)) {
            flipImage();  // Flip the image when color changes
            flipped = !flipped;  // Toggle the flipped flag to track current state
        }

        // Draw the image (flipped or normal) depending on the `flipped` flag
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}
