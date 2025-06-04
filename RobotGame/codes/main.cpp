/******************************
 Program: MadBot.h 
Course: Data Structures and Algorithms
 Trimester: 2410
 Name: 
 Abdul Adzeem Abdul Rasyid (1211109773)
 Izza Nelly binti Mohd Nasir (1211111583)
 Ain Nur Yasmin binti Muhd Zaini (1211109582)
MohammadHazman bin Khairil Anwar (1211110444)

 Lecture Section: TC2L
 Tutorial Section: TT5L, TT6L
 Email: 1211109773@student.mmu.edu.my
 Phone: 012-289 6530
*****************************/

#include "Battlefield.h"
#include "RoboCop.h"
#include "Terminator.h"
#include "BlueThunder.h"

int main() {
    srand(time(0));

    Battlefield bf(10, 10);
    RoboCop rc("RoboCop", 2, 2, &bf);
    Terminator tm("Terminator", 3, 3, &bf);
    BlueThunder bt("BlueThunder", 7, 7, &bf);

    bf.addRobot(&rc);
    bf.addRobot(&tm);
    bf.addRobot(&bt);
    cout << "The battlefield dimension is 10 x 10.\n" << "Steps: 50\n";
    cout << "This is the starting position of the robots.\n";
    bf.placeRobots();
    bf.display();

    // Simulation loop
    for (int step = 0; step < 40; ++step) {
        cout << "Turn " << step + 1 << ":\n";
        bf.respawnRobots();

        if (rc.isActive()) {
            rc.action();
        } else {
            cout << "RoboCop is not activated!\n";
        }

        if (tm.isActive()) {
            tm.action();
        } else {
            cout << "Terminator is not activated!\n";
        }

        if (bt.isActive()) {
            bt.action();
        } else {
            cout << "BlueThunder is not activated!\n";
        }

        if (tm.isDead() && bt.isDead()) {
            cout << "Terminator and BlueThunder have been eliminated.\n";
            cout << "RoboCop won!\n";
            bf.display();
            break;
        } else if (rc.isDead() && bt.isDead()) {
            cout << "RoboCop and BlueThunder have been eliminated.\n";
            cout << "Terminator won!\n";
            bf.display();
            break;
        } else if (rc.isDead() && tm.isDead()) {
            cout << "RoboCop and Terminator have been eliminated.\n";
            cout << "BlueThunder won!\n";
            bf.display();
            break;
        }

        bf.placeRobots();
        bf.display();
        cout << "\n";
    }
    return 0;
}
