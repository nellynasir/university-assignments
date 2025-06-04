#ifndef MOVE_ROBOT_H
#define MOVE_ROBOT_H

#include "Robot.h"
#include "Battlefield.h"

class MoveRobot : virtual public Robot {
protected:
    Battlefield* battlefield;
public:
    MoveRobot(string n, int x, int y, Battlefield* bf) : Robot(n, x, y), battlefield(bf) {}

    void move() override {
        int direction = rand() % 8;
        int newX = posX;
        int newY = posY;

        switch (direction) {
            case 0: newX--; break; // Up
            case 1: newX++; break; // Down
            case 2: newY--; break; // Left
            case 3: newY++; break; // Right
            case 4: newX--; newY--; break; // Up-left
            case 5: newX--; newY++; break; // Up-right
            case 6: newX++; newY--; break; // Down-left
            case 7: newX++; newY++; break; // Down-right
        }

        // Ensure the new position is within the battlefield boundaries and not occupied
        if (newX >= 0 && newX < battlefield->getRows() && newY >= 0 && newY < battlefield->getCols() && battlefield->getRobotAt(newX, newY) == nullptr) {
            posX = newX;
            posY = newY;
            cout << name << " moves to (" << posX << ", " << posY << ")\n";
        } else {
            cout << name << " tries to move to an occupied or out of bounds position and stays at (" << posX << ", " << posY << ")\n";
        }
    }
};

#endif // MOVE_ROBOT_H
