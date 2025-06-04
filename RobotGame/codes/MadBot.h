// MadBot.h
#ifndef MADBOT_H
#define MADBOT_H

#include "ShootRobot.h"
#include "LookRobot.h"
#include "Battlefield.h"
#include "RoboTank.h"

class MadBot : public ShootRobot, public LookRobot {
    Battlefield* battlefield;
public:
    MadBot(string n, int x, int y, Battlefield* bf)
        : Robot(n, x, y), ShootRobot(n, x, y), LookRobot(n, x, y), battlefield(bf) {
            bf->addRobot(this);
        }

    void move() override {
        // MadBot does not move
    }

    void fire() override {
        // Fire at one of the immediate neighboring locations
        int dx[8] = {-1, -1, -1, 0, 0, 1, 1, 1};
        int dy[8] = {-1, 0, 1, -1, 1, -1, 0, 1};

        int direction = rand() % 8;
        int targetX = posX + dx[direction];
        int targetY = posY + dy[direction];

        if (battlefield->isValidCoordinate(targetX, targetY)) {
            Robot* targetRobot = battlefield->getRobotAt(targetX, targetY);
            if (targetRobot != nullptr && targetRobot->getName() != name) {
                cout << name << " fires at (" << targetX << ", " << targetY << ") and hits " << targetRobot->getName() << "!\n";
                battlefield->removeRobot(targetRobot);
                incrementKills();
                if (getKills() >= 3) {
                    upgradeToRoboTank();
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
        return 'M'; // Display character for MadBot
    }

private:
    void upgradeToRoboTank() {
        // Remove the current MadBot from the battlefield
        battlefield->removeRobot(this);

        // Create a new RoboTank at the same position
        RoboTank* upgradedRobot = new RoboTank(name, posX, posY, battlefield);

        // Transfer relevant data to the new upgraded robot
        upgradedRobot->setKills(getKills());

        // Add the upgraded robot to the battlefield
        battlefield->addRobot(upgradedRobot);

        cout << name << " has been upgraded to RoboTank!\n";
    }
};

#endif // MADBOT_H
