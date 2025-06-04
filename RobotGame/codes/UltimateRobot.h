// UltimateRobot.h
#ifndef ULTIMATEROBOT_H
#define ULTIMATEROBOT_H

#include "MoveRobot.h"
#include "ShootRobot.h"
#include "LookRobot.h"
#include "StepRobot.h"
#include "Battlefield.h"

class UltimateRobot : public MoveRobot, public ShootRobot, public LookRobot, public StepRobot {
    Battlefield* battlefield;
public:
    UltimateRobot(string n, int x, int y, Battlefield* bf) 
        : Robot(n, x, y), MoveRobot(n, x, y, bf), ShootRobot(n, x, y), LookRobot(n, x, y), StepRobot(n, x, y), battlefield(bf) {
            bf->addRobot(this);
        }

    void fire() override {
        int maxRange = 10;
        for (int i = 0; i < 3; ++i) {
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
    }

    void stepping() override {
        Robot* targetRobot = battlefield->getRobotAt(posX, posY);
        if (targetRobot != nullptr && targetRobot->getName() != name) {
            cout << name << " steps on and hits " << targetRobot->getName() << " at (" << posX << ", " << posY << ")!\n";
            battlefield->removeRobot(targetRobot);
            incrementKills();
        } else {
            cout << name << " tries to step but finds no enemy at (" << posX << ", " << posY << ")!\n";
        }
    }

    void action() override {
        look();
        move();
        for (int i = 0; i < 3; ++i) fire();
        for (int i = 0; i < 1; ++i) stepping();
        cout << name << " has " << getKills() << " kills.\n";
    }

    char getDisplayChar() const override {
        return 'U'; // Display character for UltimateRobot
    }
};

#endif // ULTIMATEROBOT_H