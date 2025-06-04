package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

import piece.Piece;
import piece.Ram;
import piece.Tor;
import piece.Sau;
import piece.Xor;
import piece.Biz;

// GamePanel class manages the main game logic and graphical user interface.
// This class is responsible for rendering the chessboard, handling user interactions,
// and controlling the game flow using a game loop.

// Design Pattern: This class follows the Observer Pattern, as it observes player mouse actions
// and updates the game state accordingly.

// Written by : All
// All Other Methods : Mali, Nelly
// Methods related to Controller Class : Sara
// GUI : Fiza

public class GamePanel extends JPanel implements Runnable {

    // Frame Rate Constants
    final int FPS = 60;
    Thread gameThread;

    // Board and Pieces
    Board board = new Board();
    Mouse mouse = new Mouse();
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activeP;

    // Player Color Constants
    public static final int BLUE = 0;
    public static final int RED = 1;

    // Game State Variables
    private int saveNumber;    
    private String bluePlayer;  
    private String redPlayer;
    private int currentColor = BLUE;  // Default Player Starting with Blue
    
    // UI Components
    private JButton saveButton, returnButton;
    private JPanel cardPanel;
    private Controller controller;
    
    // Movement Control Flags
    boolean canMove;
    boolean validSquare;
    
    public GamePanel(JPanel cardPanel, Controller controller) { 

        // Constructor for GamePanel.
        this.cardPanel = cardPanel; // cardPanel  JPanel for switching views
        this.controller = controller; // controller Controller for handling game logic

        setPreferredSize(new Dimension(1200, 790));
        setBackground(new Color(34, 34, 44));
        setLayout(new BorderLayout());  // Set no layout manager to use absolute positioning
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        initializeUI(); // MVC Implementation
        initializeGameState(controller);
        copyPieces(pieces, simPieces);
    }

    // Initializes UI components including menu and board panels
    private void initializeUI() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(44, 44, 54));
        menuPanel.setPreferredSize(new Dimension(400,790));
        add(menuPanel, BorderLayout.EAST);
    
        // Create board panel
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
    
                // Draw the board with the current panel size
                board.draw(g2);

                Piece.setCurrentColor(currentColor);
                for (Piece p : simPieces) {
                    p.draw(g2);
                } 
                
                if(activeP != null){
                    if (canMove){
                        g2.setColor(new Color(247, 220, 111, 180));
                        g2.fillRect(activeP.col*Board.SQUARE_SIZE, activeP.row*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                    }
                    activeP.draw(g2); 
                }
            }
        };
        // boardPanel.setPreferredSize(new Dimension(800, 800));
        boardPanel.setBackground(new Color(34, 34, 44));
        add(boardPanel, BorderLayout.CENTER);

        initializeGameState(controller);
        copyPieces(pieces, simPieces);

        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton gameMenuButton = new JButton("Game Menu");
        gameMenuButton.setFont(new Font("Arial", Font.PLAIN, 26));
        gameMenuButton.setBackground(new Color(44, 44, 54)); // Match the background color
        gameMenuButton.setForeground(Color.white);
        gameMenuButton.setFocusPainted(false);
        gameMenuButton.setEnabled(false); // Disable button to make it non-clickable
        gameMenuButton.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Padding
        gameMenuButton.setPreferredSize(new Dimension(250,50));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0,10);
        gbc.anchor = GridBagConstraints.CENTER;
        menuPanel.add(gameMenuButton, gbc);

        saveButton = new JButton("Save Game");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));  // Adjust font size
        Dimension buttonSize = new Dimension(200, 30);  // Button size
        saveButton.setPreferredSize(buttonSize);
        saveButton.setMinimumSize(buttonSize);
        saveButton.setMaximumSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets (10,10,0,10);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNumber = controller.saveGame(saveNumber, currentColor, bluePlayer, redPlayer, GamePanel.this);
            }
        });
        menuPanel.add(saveButton, gbc);

        returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.PLAIN, 16));  // Adjust font size
        returnButton.setPreferredSize(buttonSize);
        returnButton.setMinimumSize(buttonSize);
        returnButton.setMaximumSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20,10,0,10);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cardPanel.getLayout();   
                cl.show(cardPanel, "Controller");
            }
        });
        menuPanel.add(returnButton, gbc);
    
    }

    // Starts the game loop in a separate thread
    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Initializes game state variables from the controller
    private void initializeGameState(Controller controller) {
        this.bluePlayer = controller.getBluePlayer();
        this.redPlayer = controller.getRedPlayer();
        this.currentColor = controller.getCurrentColor();
        this.saveNumber = controller.getSaveNumber();
    }

    // Setters Method // check this later sara
    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }

    public void setBluePlayer(String bluePlayer) {
        this.bluePlayer = bluePlayer;
    }

    public void setRedPlayer(String redPlayer) {
        this.redPlayer = redPlayer;
    }

    public void setSaveNumber(int saveNumber) {
        this.saveNumber = saveNumber;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    // Initialize default pieces for a new game
    public void initializeDefaultPieces() {
        pieces.clear();
        currentColor = BLUE;

        // Blue Player
        pieces.add(new Ram(BLUE, 6, 0));
        pieces.add(new Ram(BLUE, 6, 1));
        pieces.add(new Ram(BLUE, 6, 2));
        pieces.add(new Ram(BLUE, 6, 3));
        pieces.add(new Ram(BLUE, 6, 4));

        pieces.add(new Xor(BLUE, 7, 0));
        pieces.add(new Biz(BLUE, 7, 1));
        pieces.add(new Sau(BLUE, 7, 2));
        pieces.add(new Biz(BLUE, 7, 3));
        pieces.add(new Tor(BLUE, 7, 4));

        // Red Player
        pieces.add(new Ram(RED, 1, 0));
        pieces.add(new Ram(RED, 1, 1));
        pieces.add(new Ram(RED, 1, 2));
        pieces.add(new Ram(RED, 1, 3));
        pieces.add(new Ram(RED, 1, 4));

        pieces.add(new Tor(RED, 0, 0));
        pieces.add(new Biz(RED, 0, 1));
        pieces.add(new Sau(RED, 0, 2));
        pieces.add(new Biz(RED, 0, 3));
        pieces.add(new Xor(RED, 0, 4));
    }  
    
    // Load pieces read from gamedata.txt fetched from Controller
    public void loadPieces(ArrayList<Piece> loadedPieces) {
        pieces.clear();  // Clear previous pieces
        for (Piece piece : loadedPieces) {
            pieces.add(piece);  
        }
        copyPieces(pieces, simPieces);  // Copy loaded pieces to simPieces
        repaint();  // Trigger a repaint to update the UI
    }    
    
    // Copy pieces to simulation array
    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();  // Clear the target list before copying
        for (Piece piece : source) {
            target.add(piece);  // Copy each piece to the target list (simPieces)
        }
    }

    // Implements the game loop for continuous rendering
    // Design pattern : Runnable
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // Handles game updates such as player moves
    // Design Pattern: Command Pattern
    // triggered by user inputs or game events
    private void update() {
       if (mouse.pressed){
           if(activeP == null){
                //If the activeP is null, check if you can pickup a piece
                for(Piece piece :simPieces){
                    //If the mouse is on an ally piece, pick itup as the activeP
                    if(piece.color == currentColor && piece.col == mouse.x/Board.SQUARE_SIZE && piece.row == mouse.y/Board.SQUARE_SIZE) {
                        activeP = piece;
                    }
                }
           }    
           else{
               simulate();  //If the player is holding a piece, simulate the move
           }
       }
       
       // Mouse button is released
       if (mouse.pressed == false){
            if (activeP != null){
                
                if (validSquare){
                    // Move comfirmed and validated by system
                    // Update the place list in case a piece has been captured and removed during the simulation
                    activeP.updatePosition();
                    copyPieces(simPieces, pieces);
                    changePlayer();
                    Piece.flipPieces(pieces);
                    copyPieces(pieces, simPieces);
                }
                else{
                    copyPieces(pieces, simPieces);
                    activeP.resetPosition();
                }
                activeP = null;
            }
        }
    }

    // Switches turn to the next player
    // Design Pattern: Observer Pattern
    private void changePlayer() {
        currentColor = (currentColor == BLUE) ? RED : BLUE;
    }

    // Deletes a saved game slot by calling the controller's deleteGame method
    // saveNumber The identifier of the save slot to be deleted
    // Design Pattern: Command Pattern
    public void deleteGameSlot(int saveNumber) {
        controller.deleteGame(saveNumber, cardPanel, true);  // Now `cardPanel` is accessible
    }

    // Simulates a move by the active piece, checking for valid movements and handling captures
    // Updates piece positions and handles win conditions if the opponent's key piece is captured
    private void simulate(){
        canMove = false;
        validSquare = false;

        //Reset the pieces list in every loop. This is basically for restoring during the simulation
        copyPieces(pieces, simPieces);
        
        //If a piece is being held, update the positions based on mouse movemenet
        activeP.row = activeP.getRow(activeP.y);
        activeP.col = activeP.getCol(activeP.x);
        activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
        activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;

        //Check if the piece is hovering over a reacheable square
        if(activeP.canMove(activeP.row, activeP.col)){
            canMove = true;

            //If hitting a piece, remove it from the list
            if(activeP.hittingP != null){
                if(activeP.hittingP instanceof Sau){ 
                    if (activeP.hittingP instanceof Sau) {
                        // Check if the captured piece is the opponent's SAU piece, ending the game
                        String winner = (activeP.color == BLUE) ? "main/imagegame/bluewin.png" : "main/imagegame/redwin.png";
                        showWinnerScreen(winner); // MVC implementation
                        gameThread = null;  // Stop game loop
                        deleteGameSlot(saveNumber); // Indicate that the game ended in victory
                        currentColor = -1;
                        return;
                    }
                }
                // Remove the captured piece from the simulated pieces list
                simPieces.remove(activeP.hittingP.getIndex()); 
            }
            validSquare = true;
        }
    }

    // Displays the winner screen with an image based on the winner and allows the user to return to the main menu
    // winnerImagePath Path to the winner's image to be displayed
    // Design Pattern: Command Pattern
    private void showWinnerScreen(String winnerImagePath) {
        JPanel winnerPanel = new JPanel(new BorderLayout());
    
        // Load the original image
        ImageIcon originalIcon = new ImageIcon(winnerImagePath);
        Image originalImage = originalIcon.getImage();
        
        // Get cardPanel dimensions to resize the image
        int panelWidth = cardPanel.getWidth();
        int panelHeight = cardPanel.getHeight();
        
        // Scale the image while maintaining aspect ratio
        Image scaledImage = originalImage.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JLabel label = new JLabel(scaledIcon);
    
        // Return button to go back to the main menu
        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cardPanel.getLayout(); // Switch back to the main controller panel
            cl.show(cardPanel, "Controller");
        });
    
        // Add components to the panel
        winnerPanel.add(label, BorderLayout.CENTER);
        winnerPanel.add(returnButton, BorderLayout.SOUTH);
    
        // Add the winner panel to the cardPanel and show it
        cardPanel.add(winnerPanel, "WinnerScreen");
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "WinnerScreen");
    }
}