#ifndef STEP_ROBOT_H
#define STEP_ROBOT_H

#include "Robot.h"

class StepRobot : virtual public Robot {
public:
    StepRobot(string n, int x, int y) : Robot(n, x, y) {}
    void stepping() {
        int targetX = posX;
        int targetY = posY;
        cout << name << " steps on another robot at (" << posX << ", " << posY << ")\n";
    }
};

#endif // STEP_ROBOT_H


  
