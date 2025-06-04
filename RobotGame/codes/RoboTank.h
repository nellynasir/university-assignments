// RoboTank.h
#ifndef ROBOTANK_H
#define ROBOTANK_H

#include "ShootRobot.h"
#include "LookRobot.h"
#include "Battlefield.h"
#include "UltimateRobot.h"

class RoboTank : public ShootRobot, public LookRobot {
    Battlefield* battlefield;
public:
    RoboTank(string n, int x, int y, Battlefield* bf)
        : Robot(n, x, y), ShootRobot(n, x, y), LookRobot(n, x, y), battlefield(bf) {
            bf->addRobot(this);
        }

    void move() override {
        // RoboTank does not move
    }

    void fire() override {
        int maxRange = battlefield->getWidth() > battlefield->getHeight() ? battlefield->getWidth() : battlefield->getHeight();
        int targetX = posX + (rand() % (maxRange * 2 + 1)) - maxRange;
        int targetY = posY + (rand() % (maxRange * 2 + 1)) - maxRange;

        if (battlefield->isValidCoordinate(targetX, targetY)) {
            Robot* targetRobot = battlefield->getRobotAt(targetX, targetY);
            if (targetRobot != nullptr && targetRobot->getName() != name) {
                cout << name << " fires at (" << targetX << ", " << targetY << ") and hits " << targetRobot->getName() << "!\n";
                battlefield->removeRobot(targetRobot);
                incrementKills();
                if (getKills() >= 3) {
                    upgradeToUltimateRobot();
                }
            } else {
                cout << name << " fires at (" << targetX << ", " << targetY << ") but misses!\n";
            }
        } else {
            cout << name << " tries to fire at (" << targetX << ", " << targetY << ") but it's out of range!\n";
        }
    }

    void action() override {
        look();
        fire();
        cout << name << " has " << getKills() << " kills.\n";
    }

    char getDisplayChar() const override {
        return 'R'; // Display character for RoboTank
    }

private:
    void upgradeToUltimateRobot() {
        // Remove the current RoboTank from the battlefield
        battlefield->removeRobot(this);

        // Create a new UltimateRobot at the same position
        UltimateRobot* upgradedRobot = new UltimateRobot(name, posX, posY, battlefield);

        // Transfer relevant data to the new upgraded robot
        upgradedRobot->setKills(getKills());

        // Add the upgraded robot to the battlefield
        battlefield->addRobot(upgradedRobot);

        cout << name << " has been upgraded to UltimateRobot!\n";
    }
};

#endif // ROBOTANK_H
