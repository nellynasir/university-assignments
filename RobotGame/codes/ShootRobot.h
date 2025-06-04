#ifndef SHOOT_ROBOT_H
#define SHOOT_ROBOT_H

#include "Robot.h"

class ShootRobot : virtual public Robot {
public:
    ShootRobot(string n, int x, int y) : Robot(n, x, y) {}
    void fire() override {
        int targetX = posX + (rand() % 3 - 1);
        int targetY = posY + (rand() % 3 - 1);
        cout << name << " fires at (" << targetX << ", " << targetY << ")\n";
    }
};

#endif // SHOOT_ROBOT_H
