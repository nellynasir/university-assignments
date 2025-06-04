package main;

import java.awt.*;

//  Represents a chessboard in a graphical application.

//  This class handles the drawing of a chessboard grid using the specified color scheme.
//  It ensures alternate squares are drawn in distinct colors to represent a typical chessboard.
//  The size of each square is defined by the constant SQUARE_SIZE.

//  Design Pattern: This class follows the Template Method Pattern by defining a standard 
//  way to draw the board, which could be extended or reused in subclasses for variations 
//  in board appearance or layout.

// Written by : Mali, Nelly, Fiza
public class Board {

    // Constants defining the pixel size of each square on the board.
    public static final int SQUARE_SIZE = 95;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

    // White and light grey color palette for the chessboard squares
    private static final Color LIGHT_SQUARE_START = new Color(255, 255, 255);  // Pure white for light squares
    private static final Color LIGHT_SQUARE_END = new Color(255, 255, 230);    // Very light pastel yellow for depth
    private static final Color DARK_SQUARE_START = new Color(210, 210, 210);  // Light grey for dark squares
    private static final Color DARK_SQUARE_END = new Color(230, 230, 230);    // Slightly deeper light grey for depth

    // Color for the shadow effect (very subtle)
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 15);  // Very light shadow effect for a soft appearance

    // Border color for the squares (light grey for subtle contrast)
    private static final Color BORDER_COLOR = new Color(200, 200, 210);  // Soft light grey for borders

    public void draw(Graphics2D g2) {
        // Enable anti-aliasing for smoother rendering of shapes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Loop through rows of the chessboard
        for (int row = 0; row < 8; row++) {

            // Loop through columns of the chessboard
            for (int col = 0; col < 5; col++) {

                // Alternate the color based on the row and column index
                if ((row + col) % 2 == 0) {
                    // Apply gradient for light squares (white to light pastel yellow)
                    GradientPaint lightGradient = new GradientPaint(
                        col * SQUARE_SIZE, row * SQUARE_SIZE, LIGHT_SQUARE_START,
                        (col + 1) * SQUARE_SIZE, (row + 1) * SQUARE_SIZE, LIGHT_SQUARE_END
                    );
                    g2.setPaint(lightGradient);
                } else {
                    // Apply gradient for dark squares (light grey)
                    GradientPaint darkGradient = new GradientPaint(
                        col * SQUARE_SIZE, row * SQUARE_SIZE, DARK_SQUARE_START,
                        (col + 1) * SQUARE_SIZE, (row + 1) * SQUARE_SIZE, DARK_SQUARE_END
                    );
                    g2.setPaint(darkGradient);
                }

                // Draw the square with gradient
                g2.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                // Add very subtle shadow effect around each square
                g2.setColor(SHADOW_COLOR);
                g2.fillRoundRect(
                    col * SQUARE_SIZE + 3, row * SQUARE_SIZE + 3, SQUARE_SIZE, SQUARE_SIZE, 10, 10
                );

                // Draw the soft, light border around each square
                g2.setColor(BORDER_COLOR);
                g2.drawRoundRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, 10, 10);
            }
        }
    }
}
