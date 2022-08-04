package it.unibo.aknightstale.models.entity;

public interface MovableEntity extends EntityModel {
    double getSpeed();
    void setSpeed(double speed);
    Direction getDirection();
    void goUp();
    void goDown();
    void goLeft();
    void goRight();
}