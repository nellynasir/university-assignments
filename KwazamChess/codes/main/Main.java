package main;

import java.awt.*;
import javax.swing.*;

// The Main class serves as the entry point for the Kwazam Chess application.
// It initializes the main application window and manages screen transitions 
// using a CardLayout for multiple panels.

// Design Patterns : Singleton (for ensuring a single application instance via JFrame)

// Written by : Mali, Sara
public class Main
{
    public static void main(String[] args){
        // Create the main application window (Singleton pattern ensuring a single frame)
        JFrame window = new JFrame("Kwazam Chess!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 800);

        // Create the main container panel with CardLayout for switching screens
        JPanel cardPanel = new JPanel(new CardLayout());
        window.add(cardPanel);

        // Instantiate the Controller
        Controller controller = new Controller(cardPanel);

        // Add the Controller panel to the CardLayout with an identifier "Controller"
        cardPanel.add(controller, "Controller");

        window.add(cardPanel); // Add cardPanel to the window
        window.setLocationRelativeTo(null); // Ensure the window appears at the center of the screen
        window.setVisible(true); // Make the application window visible
    }
    
}
