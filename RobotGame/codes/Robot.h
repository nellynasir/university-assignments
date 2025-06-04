// Robot.h
#ifndef ROBOT_H
#define ROBOT_H

#include <string>
#include <iostream>
#include <cstdlib>
#include <ctime>

using namespace std;

class Robot {
protected:
    string name;
    int posX, posY;
    int lives;
    int kills;
    int deathCount;
    bool active; // To track if the robot is active or waiting to respawn

public:
    Robot(string n, int x, int y) : name(n), posX(x), posY(y), lives(3), kills(0), deathCount(0), active(true) {}
    virtual ~Robot() {}

    string getName() const { return name; }
    int getX() const { return posX; }
    int getY() const { return posY; }
    int getLives() const { return lives; }
    void loseLife() { 
        if (lives > 0) {
            lives--;
            deactivate();
        } 
    }
    bool isAlive() { return lives > 0; }
    bool isDead() { return lives <= 0;}
    void incrementKills() { kills++; }
    int getKills() const { return kills; }
    void incrementDeaths() { deathCount++; }
    int getDeaths() const { return deathCount; }
    void setPosition(int x, int y) { posX = x; posY = y; }
    bool isActive() const { return active; }
    void deactivate() { active = false; }
    void activate() { active = true; }
    void setKills(int k) { kills = k;}

    virtual char getDisplayChar() const = 0;

    virtual void look() = 0;
    virtual void move() = 0;
    virtual void fire() = 0;
    virtual void stepping() = 0;
    virtual void action() = 0;
};

#endif // ROBOT_H
