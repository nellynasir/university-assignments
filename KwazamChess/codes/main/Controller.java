package main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import piece.Biz;
import piece.Piece;
import piece.Ram;
import piece.Sau;
import piece.Tor;
import piece.Xor;

// Design Pattern : Controller
// This class acts as the controller in the Model-View-Controller (MVC) pattern.
// It manages the flow of the game and the interactions between the game interface (View) and the underlying game logic (Model).
// The Controller class is responsible for initializing the game panel, handling button actions, switching between game views, and managing game states.

// Written by : Sara
// GUI & MVC Written by : Fiza
public class Controller extends JPanel {

    // Initialize game-related variables
    private GamePanel gamePanel;
    private String bluePlayer, redPlayer;
    private int saveNumber, currentColor;
    public static ArrayList<Piece> pieces = new ArrayList<>(); // List of all pieces
    public static final int BLUE = 0, RED = 1; // Constants to represent blue and red players

    // Initialize UI buttons for start and load actions
    private JButton startButton, loadButton;

    // Constructor for initializing the controller panel and buttons
    public Controller(JPanel cardPanel) {
        // Set layout for this panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 10);

        // Initialize the game panel
        gamePanel = new GamePanel(cardPanel, this);
        setBackground(new Color(34, 34, 44));

        // Add background image to the controller panel
        ImageIcon originalIcon = new ImageIcon("main/imagegame/kwazamimage.png");
        Image resizedImage = originalIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        add(imageLabel,gbc);

        // Initialize the "Start New Game" button
        startButton = new JButton("Start New Game");
        Dimension buttonSize = new Dimension(500,50);
        startButton.setPreferredSize(buttonSize);
        startButton.setMinimumSize(buttonSize);
        startButton.setMaximumSize(buttonSize);
        startButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        gbc.gridy = 1;
        gbc.insets = new Insets(0,10,10,10);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNewGamePanel(cardPanel); // Switch to the player name input panel
            }
        });
        add(startButton, gbc);

        // Initialize the "Load Saved Game" button
        loadButton = new JButton("Load Saved Game");
        loadButton.setPreferredSize(buttonSize);
        loadButton.setMinimumSize(buttonSize);
        loadButton.setMaximumSize(buttonSize);
        loadButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        gbc.gridy = 2;
        gbc.insets = new Insets(10,10,100,10);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoadGamePanel(cardPanel); // Switch to the load game panel
            }
        });
        add(loadButton, gbc);

        // Re-initialize gamePanel /// CHECK IF NECESSARY
        gamePanel = new GamePanel(cardPanel, this);
    }

    // Method to display the panel for entering new game player names
    private void showNewGamePanel(JPanel cardPanel) {
        
        // Create the new game panel where players will input their names
        JPanel newGamePanel = new JPanel(new GridBagLayout());
        newGamePanel.setBackground(new Color(34, 34, 44));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Labels for Blue and Red Player
        JLabel bluePlayerLabel = new JLabel("Enter Blue Player Name:");
        bluePlayerLabel.setForeground(Color.WHITE);
        bluePlayerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        newGamePanel.add(bluePlayerLabel,gbc);

        JTextField bluePlayerField = new JTextField(20);
        Dimension fieldSize = new Dimension(700, 30);
        bluePlayerField.setPreferredSize(fieldSize);
        bluePlayerField.setMinimumSize(fieldSize);
        bluePlayerField.setMaximumSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        newGamePanel.add(bluePlayerField, gbc);

        JLabel redPlayerLabel = new JLabel("Enter Red Player Name:");
        redPlayerLabel.setForeground(Color.WHITE);
        redPlayerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        newGamePanel.add(redPlayerLabel, gbc);

        JTextField redPlayerField = new JTextField(20);
        redPlayerField.setPreferredSize(fieldSize);
        redPlayerField.setMinimumSize(fieldSize);
        redPlayerField.setMaximumSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        newGamePanel.add(redPlayerField, gbc);

        // Button to start the game after player names are entered
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(200,50));
        startGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start a new game with the input player names
                String bluePlayerName = bluePlayerField.getText().trim();
                String redPlayerName = redPlayerField.getText().trim();
                 currentColor = 0;
                if (bluePlayerName.trim().isEmpty() || redPlayerName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(newGamePanel, "Please enter both player names!");
                } else {
                    // Set game settings
                    setCurrentColor(0);  // BLUE starts first
                    setSaveNumber(0);    // Unsaved game set SaveNumber = 0
                    setBluePlayer(bluePlayerName);
                    setRedPlayer(redPlayerName);

                    // Initialize default pieces and launch the game
                    gamePanel.initializeDefaultPieces(); 
                    GamePanel gamePanel = new GamePanel(cardPanel, Controller.this);
                    cardPanel.add(gamePanel, "GamePanel");
                    gamePanel.launchGame();

                    // Switch to the game panel
                    CardLayout cl = (CardLayout) cardPanel.getLayout();
                    cl.show(cardPanel, "GamePanel");  
                }
            }
        });
        newGamePanel.add(startGameButton, gbc);

        // Button to return to the controller panel
        JButton returnButton = new JButton("Return");
        returnButton.setPreferredSize(new Dimension(200,50));
        returnButton.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        newGamePanel.add(returnButton, gbc);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to the main controller panel
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "Controller");
            }
        });
        // Add new game panel and show it
        cardPanel.add(newGamePanel, "NewGamePanel");
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "NewGamePanel");
    }

    // Method to display the panel for loading a saved game
    private void showLoadGamePanel(JPanel cardPanel) {
        // Create the load game panel with slots for each saved game
        JPanel loadGamePanel = new JPanel(new GridBagLayout());
        loadGamePanel.setBackground(new Color(34, 34, 44));
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loadLabel = new JLabel("Load Saved Game");
        loadLabel.setForeground(Color.WHITE); // Set text color
        loadLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loadGamePanel.add(loadLabel, gbc);

        // Load saved games into available slots
        int slotCount = 10;  // Assuming max 10 slots for saved games

        // For each slot, load the saved game data (using loadGame function for each slot)
        for (int slotIndex = 1; slotIndex <= slotCount; slotIndex++) {
            Map<String, Object> gameState = loadGame(slotIndex); 

            if (gameState != null) {    
                // Extract saved data (pieces, player names, etc.)
                @SuppressWarnings("unchecked")
                ArrayList<Piece> loadedPieces = (ArrayList<Piece>) gameState.get("pieces"); 
                int currentColor = (int) gameState.get("currentColor");
                String bluePlayer = (String) gameState.get("bluePlayer");
                String redPlayer = (String) gameState.get("redPlayer");
                int saveNumber = slotIndex;

                // Create the button with the loaded game details
                JButton slotButton = new JButton("Slot " + slotIndex + ",  Blue: " + bluePlayer + ", Red: " + redPlayer);
                slotButton.setFont(new Font("Sans Serif", Font.BOLD, 15));
                Dimension buttonSize = new Dimension(500,50);
                slotButton.setPreferredSize(buttonSize);
                slotButton.setMinimumSize(buttonSize);
                slotButton.setMaximumSize(buttonSize);
                gbc.gridx = 0;
                gbc.gridy = slotIndex;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                loadGamePanel.add(slotButton, gbc);

                // Action listener to load the selected game and switch to GamePanel
                slotButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        GamePanel gamePanel = new GamePanel(cardPanel, Controller.this);
                        gamePanel.loadPieces(loadedPieces);  // Load pieces based on the loaded ArrayList
                        gamePanel.setCurrentColor(currentColor);  // Set the Current Color
                        gamePanel.setSaveNumber(saveNumber);  // Set the Save Number
                        gamePanel.setBluePlayer(bluePlayer);  // Set the Blue Player
                        gamePanel.setRedPlayer(redPlayer);    // Set the Red Player
                        gamePanel.launchGame();

                        // Switch to the game panel
                        cardPanel.add(gamePanel, "GamePanel");
                        CardLayout cl = (CardLayout) cardPanel.getLayout();
                        cl.show(cardPanel, "GamePanel");
                    }
                });
            }
        }

        // Button to delete a saved game
        JButton deleteButton = new JButton("Delete Game");
        deleteButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        Dimension buttonSize = new Dimension(500,50);
        deleteButton.setPreferredSize(buttonSize);
        deleteButton.setMinimumSize(buttonSize);
        deleteButton.setMaximumSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = slotCount + 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loadGamePanel.add(deleteButton, gbc);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                 // Prompt user for slot number and delete the game
                String input = JOptionPane.showInputDialog(loadGamePanel, "Enter Save Slot Number to Delete:");
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        int slotToDelete = Integer.parseInt(input.trim());
                        deleteGame(slotToDelete, cardPanel, false); // Delete the game from the selected slot
                        JOptionPane.showMessageDialog(loadGamePanel, "Save Slot " + slotToDelete + " deleted. Please reload the panel.");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(loadGamePanel, "Invalid input. Please enter a valid number.");
                    }
                }
            }
        });    

        // Return button to go to the main controller panel
        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Sans Serif", Font.BOLD, 20));
        returnButton.setPreferredSize(buttonSize);
        returnButton.setMinimumSize(buttonSize);
        returnButton.setMaximumSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = slotCount + 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loadGamePanel.add(returnButton, gbc);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to the main controller panel
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "Controller");
            }
        });
        // Add the load game panel to the card panel and show it
        cardPanel.add(loadGamePanel, "LoadGamePanel");
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "LoadGamePanel");
    }

    // This method is responsible for loading a saved game from a file.
    // It reads the game data from a text file, extracts the relevant game state,
    // and returns the game state in the form of a Map containing key-value pairs for 
    // the pieces on the board, the current player's color, and player names

    public Map<String, Object> loadGame(int saveNumber) {
        // Create a Map to hold the loaded game state
        Map<String, Object> result = new HashMap<>();
        // Create a list to hold the loaded pieces for the game board
        ArrayList<Piece> loadedPieces = new ArrayList<>();

        try { 
            // Read the entire file into a list of strings (each line in the file)
            List<String> allLines = new ArrayList<>();
            Scanner fileReader = new Scanner(new File("main/gamedata.txt"));
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                allLines.add(line);
            }
            fileReader.close();

            // Look for the section corresponding to the specified save number
            for (int i = 0; i < allLines.size(); i++) {
                // Check if the current line matches the save number identifier
                if (allLines.get(i).trim().equals("Save Game : " + saveNumber)) {
                    // Read and set the current player's turn (either BLUE or RED)
                    String playerTurn = allLines.get(i + 1).split(":")[1].trim();
                    if (playerTurn.equals("BLUE")) {
                        currentColor = 0;  // BLUE player is represented by 0
                    } else if (playerTurn.equals("RED")) {
                        currentColor = 1;  // RED player is represented by 1
                    }
                    // Extract player names for BLUE and RED
                    bluePlayer = allLines.get(i + 2).split(":")[1].trim();
                    redPlayer = allLines.get(i + 3).split(":")[1].trim();

                    // The board starts 5 lines after the save game section
                    int boardStartIndex = i + 5;  

                    for (int row = 0; row < 8; row++) { // Loop through each row of the board (8 rows total)                      
                        String[] pieces = allLines.get(boardStartIndex + row).trim().split("\\s+");
                        for (int col = 0; col < 5; col++) { // Loop through each column in the row (5 columns total)
                            String cell = pieces[col];
                            if (cell.equals("----")) continue; // Skip empty cells (no piece)
                    
                            // Add pieces to the ArrayList based on the loaded data
                            if (cell.startsWith("R")) { // RED pieces
                                switch (cell) {
                                    case "RTOR":
                                        loadedPieces.add(new Tor(RED, row, col));
                                        break;
                                    case "RBIZ":
                                        loadedPieces.add(new Biz(RED, row, col));
                                        break;
                                    case "RSAU":
                                        loadedPieces.add(new Sau(RED, row, col));
                                        break;
                                    case "RXOR":
                                        loadedPieces.add(new Xor(RED, row, col));
                                        break;
                                    case "RRAM":
                                        loadedPieces.add(new Ram(RED, row, col));
                                        break;
                                }
                            } else if (cell.startsWith("B")) { // BLUE pieces
                                switch (cell) {
                                    case "BTOR":
                                        loadedPieces.add(new Tor(BLUE, row, col));
                                        break;
                                    case "BBIZ":
                                        loadedPieces.add(new Biz(BLUE, row, col));
                                        break;
                                    case "BSAU":
                                        loadedPieces.add(new Sau(BLUE, row, col));
                                        break;
                                    case "BXOR":
                                        loadedPieces.add(new Xor(BLUE, row, col));
                                        break;
                                    case "BRAM":
                                        loadedPieces.add(new Ram(BLUE, row, col));
                                        break;
                                }
                            }
                        }
                    }
                    // Put the loaded game data into the result map
                    result.put("pieces", loadedPieces);
                    result.put("currentColor", currentColor);
                    result.put("bluePlayer", bluePlayer);
                    result.put("redPlayer", redPlayer);
                    return result;  // Return the map containing the loaded game data
                }
            }
        } catch (Exception e) {
            e.printStackTrace();    // Print any errors that occur during loading
        }
        return null;  // Return null if the save game section was not found or if an error occurred
    }
    
    // This method handles the saving and updating of game data. It writes the current game state, 
    // including player information, the current player's turn, and the board state, to a file.
    // If the game is already saved, it updates the existing save slot with the new game state

    // Design Pattern : Stratergy. Used indirectly by separating the logic for saving and updating 
    // the game state into different methods (`saveGame`, `updateGame`, 'saveBoardState')

    public int saveGame(int saveNumber, int currentColor, String bluePlayer, String redPlayer, GamePanel gamePanel) {
        // NEED TO EDIT SLOT HANDLING
        // if game already saved in gamedata.txt before, then update the game progress instead
        if (saveNumber != 0) {
            updateGame(saveNumber, currentColor, bluePlayer, redPlayer, gamePanel);
            return saveNumber;
        }
        try {
            // Read the existing file to determine the highest save number
            BufferedReader reader = new BufferedReader(new FileReader("main/gamedata.txt"));
            String line;
            Set<Integer> saveNumbers = new HashSet<>(); // Store all used save numbers
            int highestSaveNumber = 0; // Default to 0 if no saves exist yet
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Save Game : ")) {
                    int currentSaveNumber = Integer.parseInt(line.substring(11).trim());
                    saveNumbers.add(currentSaveNumber);
                    highestSaveNumber = Math.max(highestSaveNumber, currentSaveNumber);
                }
            }
            reader.close();
            
            // Find the smallest available save number (starting from 1)
            saveNumber = 1;
            while (saveNumbers.contains(saveNumber)) {
                saveNumber++;
            }
            
            // Write new game data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("main/gamedata.txt", true))) {
                writer.write("\n");
                writer.write("Save Game : " + saveNumber + "\n");
                String playerTurn = (currentColor == 0) ? "BLUE" : "RED";
                writer.write("Player Turn : " + playerTurn + "\n");
                writer.write("Blue Player : " + bluePlayer + "\n");
                writer.write("Red Player : " + redPlayer + "\n");
                writer.write("." + "\n");
                saveBoardState(writer, gamePanel);
                
                System.out.println("New game saved successfully!");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the game.");
            e.printStackTrace();
        }
        return saveNumber;  // Return the newly assigned save number
    }

    // Updates an existing saved game with the current game state.
    // This method replaces the existing save data for a specific save number with the current game state.
    private void updateGame(int saveNumber, int currentColor, String bluePlayer, String redPlayer, GamePanel gamePanel) {
        try {
            // Read the entire content of the file
            File file = new File("main/gamedata.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder fileContent = new StringBuilder();
            String line; 
            boolean updating = false;                 

            // Loop through the file to find and update the corresponding save game section
            while ((line = reader.readLine()) != null) {
                if (line.equals("Save Game : " + saveNumber)) {
                    updating = true;
                    fileContent.append("Save Game : ").append(saveNumber).append("\n");
                    fileContent.append("Player Turn : ").append(currentColor == 0 ? "BLUE" : "RED").append("\n");
                    fileContent.append("Blue Player : ").append(bluePlayer).append("\n");
                    fileContent.append("Red Player : ").append(redPlayer).append("\n");
                    fileContent.append(".").append("\n");
                    saveBoardState(fileContent, gamePanel);
                    fileContent.append("\n");
                } else if (line.startsWith("Save Game : ") && updating) {
                    updating = false;
                }
                // Append non-updated lines to the file content
                if (!updating) {
                    fileContent.append(line).append("\n");
                }
            }
            reader.close();
    
            // Write updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(fileContent.toString());
            }
            System.out.println("Game updated successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while updating the game.");
            e.printStackTrace();
        }
    }
    
    // Saves the current board state to the file.
    // This method generates a representation of the game board and writes it to the file
    private void saveBoardState(Appendable writer, GamePanel gamePanel) throws IOException {
        String[][] board = new String[8][5];

        // Initialize the board with empty cells ("----")
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = "----";
            }
        }
    
        ArrayList<Piece> pieces = gamePanel.getPieces();    // Retrieve the pieces from the game panel
        for (Piece piece : pieces) {    // Loop through the pieces and place them on the board
            int color = piece.getColor();
            int row = piece.row; 
            int col = piece.col;
            String prefix = (color == 0) ? "B" : "R"; // "B" for BLUE, "R" for RED
            String type = piece.getClass().getSimpleName().toUpperCase(); // Get the piece type (e.g., "TOR", "RAM")
            board[row][col] = prefix + type.substring(0, 3); // Place the piece on the board
        }
    
        // Write the board state to the file
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                writer.append(board[i][j]).append(j < 4 ? " " : "");
            }
            writer.append("\n");
        }

    }
    
    // Deletes a saved game slot from the save file. It reads the `gamedata.txt` file, 
    //removes the data corresponding to the given save number, and then updates the file
    public void deleteGame(int saveNumber, JPanel cardPanel, boolean isVictory) {
        // Input file representing the current game save data
        File inputFile = new File("main/gamedata.txt");
        // Temporary file to hold modified content
        File tempFile = new File("main/temp_gamedata.txt");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
            String line;
            boolean deleteMode = false; // Flag indicating whether the game slot is being deleted
             // Loop through each line of the save file
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("Save Game : " + saveNumber)) {
                    deleteMode = true; // Mark the start of the section to delete
                    continue;
                }
                if (deleteMode) { // Stop deleting when an empty line (end of the save slot) is encountered
                    if (line.trim().isEmpty()) {
                        deleteMode = false;
                    }
                    continue; // Skip the lines that belong to the save slot being deleted
                }
                // Write the line to the temporary file if not part of the deleted section
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting save slot " + saveNumber);
            return;
        }

        // If the original file was successfully deleted, rename the temporary file to replace it
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
            // If the game ended in victory, no need to show the load screen
            if (isVictory) { // Do nothing, as winner screen is already shown
            } else {
                // Show the load game panel (to allow the user to choose another game to load)
                showLoadGamePanel(cardPanel);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to delete the save slot.");
        }
    }
    
    // Getter and Setter methods for game variables
    public int getSaveNumber() {
        return saveNumber;
    }

    public void setSaveNumber(int saveNumber) {
        this.saveNumber = saveNumber;
    }

    public String getBluePlayer() {
        return bluePlayer;
    }

    public void setBluePlayer(String bluePlayer) {
        this.bluePlayer = bluePlayer;
    }

    public String getRedPlayer() {
        return redPlayer;
    }

    public void setRedPlayer(String redPlayer) {
        this.redPlayer = redPlayer;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }
}
