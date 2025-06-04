//BlueThunder.h
#ifndef BLUETHUNDER_H
#define BLUETHUNDER_H

#include "MoveRobot.h"
#include "ShootRobot.h"
#include "StepRobot.h"
#include "LookRobot.h"
#include "Battlefield.h"

using namespace std;

class BlueThunder : public ShootRobot, public LookRobot {
    Battlefield* battlefield;
    int firingDirection; // Tracks the direction for firing (0: Up, 1: Right, ...)
public:
    BlueThunder(string n, int x, int y, Battlefield* bf) 
      :Robot(n, x, y), battlefield(bf), firingDirection(0), ShootRobot(n, x, y), LookRobot(n, x, y) {
        bf->addRobot(this);
      }

    void fire() override {
      int targetX = posX;
      int targetY = posY;

      switch (firingDirection) {
        case 0: targetY--; break; // Up
        case 1: targetX++; break; // Right
        case 2: targetY++; break; // Down
        case 3: targetX--; break; // Left
      }   

      if (battlefield->isValidCoordinate(targetX, targetY)) {
        Robot* targetRobot = battlefield->getRobotAt(targetX, targetY);
        if (targetRobot != nullptr && targetRobot->getName() != name) {
          cout << name << " fires at (" << targetX << ", " << targetY << ") and hits " << targetRobot->getName() << "!\n";
          battlefield->removeRobot(targetRobot);
          incrementKills();
        } else {
          cout << name << " fires at (" << targetX << ", " << targetY << ") but misses!\n";
        }
      } else {
        cout << name << " tries to fire at (" << targetX << ", " << targetY << ") but it's out of range!\n";
      }

      firingDirection = (firingDirection + 1) % 4; // Update direction for next turn (clockwise)
    }

    void look() override {
      // No need to implement look() as BlueThunder doesn't use it.
    }

    void move() override {
      // No need to implement move() as BlueThunder doesn't move.
    }

    void stepping()override{
      
    }

    void action() override {
      for (int i = 0; i < 1; ++i) fire();
      if (getKills() >= 3) {
          // Change the robot type (similar to RoboCop upgrade)
        }
      cout << name << " has " << getKills() << " kills.\n";
    }

    char getDisplayChar() const override {
      return 'B';
    }
};

#endif // BLUETHUNDER_H
