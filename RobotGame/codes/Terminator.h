// Terminator.h
#ifndef TERMINATOR_H
#define TERMINATOR_H

#include "MoveRobot.h"
#include "StepRobot.h"
#include "LookRobot.h"
#include "Battlefield.h"
#include "TerminatorRoboCop.h"

class Terminator : public MoveRobot, public StepRobot, public LookRobot {
    Battlefield* battlefield;
public:
    Terminator(string n, int x, int y, Battlefield* bf) 
        : Robot(n, x, y), MoveRobot(n, x, y, bf), StepRobot(n, x, y), LookRobot(n, x, y), battlefield(bf) {
            bf->addRobot(this);
        }
    
    void fire() override {
        // nothing happens here
    }

    void action() override {
        look();
        move();
        for (int i = 0; i < 1; ++i) stepping();
        cout << name << " has " << getKills() << " kills.\n";
        if (getKills() >= 3) {
            cout << name << " upgrades to TerminatorRoboCop!\n";
            //upgradeToRoboCop();
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

    char getDisplayChar() const override {
        return 'T';
    }

/*private:
    void upgradeToRoboCop() {
        // Remove the current Terminator from the battlefield
        battlefield->removeRobot(this);

        // Create a new TerminatorRoboCop at the same position
        TerminatorRoboCop* upgradedRobot = new TerminatorRoboCop(name, posX, posY, battlefield);

        // Transfer relevant data to the new upgraded robot
        upgradedRobot->setKills(getKills());

        // Add the upgraded robot to the battlefield
        battlefield->addRobot(upgradedRobot);
    }*/
};

#endif // TERMINATOR_H