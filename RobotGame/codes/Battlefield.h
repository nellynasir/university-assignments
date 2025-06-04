// Battlefield.h
#ifndef BATTLEFIELD_H
#define BATTLEFIELD_H

#include <queue>
#include "Robot.h"

using namespace std;

class Battlefield {
    int rows, cols;
    char field[11][11];
    Robot* robots[3];
    int robotCount;
    queue<Robot*> respawnQueue;

public:
    Battlefield(int r, int c) : rows(r), cols(c), robotCount(0) {
        clear();
    }

    bool isValidCoordinate(int x, int y) const {
        return (x >= 0 && x < rows && y >= 0 && y < cols);
    }

    int getRows() const {
        return rows;
    }

    int getCols() const {
        return cols;
    }
    
    void addRobot(Robot* robot) {
        if (robotCount < 3) {
            robots[robotCount++] = robot;
        }
    }

    void removeRobot(Robot* robot) {
        for (int i = 0; i < robotCount; ++i) {
            if (robots[i] == robot) {
                // Reduce lives when removed from battlefield
                robot->loseLife();
                cout << robot->getName() << " has " << robot->getLives() << " lives left!\n";
                if (robot->isAlive()) {
                    respawnQueue.push(robot);
                }
                
                // Remove robot from battlefield
                for (int j = i; j < robotCount - 1; ++j) {
                    robots[j] = robots[j + 1];
                }
                robotCount--;

                // Clear its position on the battlefield
                int x = robot->getX();
                int y = robot->getY();
                field[x][y] = '.';

                cout << robot->getName() << " has been removed from the battlefield.\n";
                break;
            }
        }
    }

    void respawnRobots() {
        while (!respawnQueue.empty() && robotCount < 3) {
            Robot* robot = respawnQueue.front();
            respawnQueue.pop();
            if (robot->isAlive()) {
                robot->activate();
                cout << robot->getName() << " has been activated!\n";

                int x, y;
                // Find an empty spot on the battlefield
                do {
                    x = rand() % rows;
                    y = rand() % cols;
                } while (getRobotAt(x, y) != nullptr);
                
                robot->setPosition(x, y);
                addRobot(robot);
                cout << robot->getName() << " has respawned at (" << x << "," << y << ") and is ready to move.\n";
            } else {
                robot->deactivate();
                cout << robot->getName() << " is not active and won't respawn.\n";
            }
        }
    }

    Robot* getRobotAt(int x, int y) {
        for (int i = 0; i < robotCount; ++i) {
            if (robots[i]->getX() == x && robots[i]->getY() == y) {
                return robots[i];
            }
        }
        return nullptr;
    }

    void placeRobots() {
        clear();
        for (int i = 0; i < robotCount; ++i) {
            Robot* robot = robots[i];
            if (robot->isActive()) {
                int x = robot->getX();
                int y = robot->getY();
                if (x >= 0 && x < rows && y >= 0 && y < cols) {
                    field[x][y] = robot->getDisplayChar();
                }
            }
        }
    }

    void display() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                cout << field[i][j] << ' ';
            }
            cout << '\n';
        }
    }

    void clear() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                field[i][j] = '.';
            }
        }
    }
};

#endif // BATTLEFIELD_H
