// RoboCop.h
#ifndef ROBOCOP_H
#define ROBOCOP_H

#include "MoveRobot.h"
#include "ShootRobot.h"
#include "StepRobot.h"
#include "LookRobot.h"
#include "Battlefield.h"
#include "TerminatorRoboCop.h"

class RoboCop : public MoveRobot, public ShootRobot, public LookRobot {
    Battlefield* battlefield;
public:
    RoboCop(string n, int x, int y, Battlefield* bf) 
        : Robot(n, x, y), MoveRobot(n, x, y, bf), ShootRobot(n, x, y), LookRobot(n, x, y), battlefield(bf) {
            bf->addRobot(this);
        }

    void fire() override {
        int maxRange = 5;
        int targetX = posX + (rand() % (maxRange * 2 + 1)) - maxRange;
        int targetY = posY + (rand() % (maxRange * 2 + 1)) - maxRange;

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
    }

    void action() override {
        look();
        move();
        for (int i = 0; i < 3; ++i) fire();
        cout << name << " has " << getKills() << " kills.\n";
        if (getKills() >= 3) {
            cout << name << " upgrades to TerminatorRoboCop!\n";
            //upgradeToRoboCop();
        }
    }

    char getDisplayChar() const override {
        return 'R';
    }

    void stepping() override{
        //dummy
    }

/*private:
    void upgradeToRoboCop() {
        // Remove the current RoboCop from the battlefield
        battlefield->removeRobot(this);

        // Create a new TerminatorRoboCop at the same position
        TerminatorRoboCop* upgradedRobot = new TerminatorRoboCop(name, posX, posY, battlefield);

        // Transfer relevant data to the new upgraded robot
        upgradedRobot->setKills(getKills());

        // Add the upgraded robot to the battlefield
        battlefield->addRobot(upgradedRobot);
    }*/
};

#endif // ROBOCOP_H