package main;

import java.awt.event.*;

// Class Mouse extends MouseAdapter to handle mouse events
// Design Pattern : Adapter. It extends MouseAdapter to simplify handling mouse events
// This class is event-driven, where mouse events are captured 
// and processed to update the mouse's position and state (pressed/released)

// Written by : Mali, Nelly
public class Mouse extends MouseAdapter
{
    // Member variables to store mouse position and pressed state
    public int x, y; // Coordinates of the mouse pointer
    public boolean pressed; // Flag to track if the mouse button is pressed
    
    // Mouse pressed event handler
    // Sets 'pressed' flag to true and stores the x, y coordinates when the mouse button is pressed
    @Override
    public void mousePressed(MouseEvent e){
        pressed= true; // Mouse button is pressed
        x = e.getX();
        y = e.getY();
    }

    // Mouse released event handler
    // Sets 'pressed' flag to false and stores the x, y coordinates when the mouse button is released
    @Override
    public void mouseReleased(MouseEvent e){
        pressed = false; // Mouse button is released
        x = e.getX();
        y = e.getY();
    }

    // Mouse dragged event handler
    // Updates the x, y coordinates as the mouse is dragged (while the mouse button is held down)
    @Override
    public void mouseDragged(MouseEvent e){
        x = e.getX();
        y = e.getY();
    }
    
    // Mouse moved event handler
    // Updates the x, y coordinates when the mouse moves without pressing any button
    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();
    }
}
