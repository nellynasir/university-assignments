#ifndef LOOK_ROBOT_H
#define LOOK_ROBOT_H

#include "Robot.h"

class LookRobot : virtual public Robot {
public:
    LookRobot(string n, int x, int y) : Robot(n, x, y) {}
    void look() override {
        cout << name << " looks around at (" << posX << ", " << posY << ")\n";
    }
};

#endif // LOOK_ROBOT_H
