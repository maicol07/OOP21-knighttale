package it.unibo.aknightstale.models.entity;

import it.unibo.aknightstale.utils.Borders;
import it.unibo.aknightstale.utils.Point2D;
import javafx.geometry.Bounds;


public class ObstacleEntity extends EntityImpl{

    //private Point2D position;

    public ObstacleEntity(/*final Point2D position, */final Borders borders){
        super(borders, EntityType.OBSTACLE, true);
        //super.setPosition(position);
        //this.position = position;
    }

    /*@Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Point2D p) {
        this.position = p;
    }

    @Override
    public Borders getBorders() {
        return null;
    }

    /*@Override
    public void setPosition(final Point2D p) {
        this.position = p;
    }*/

    /*@Override
    public Bounds getBounds() {
        return null;
    }

    @Override
    public EntityType getType() {
        return EntityType.OBSTACLE;
    }

    @Override
    public Boolean isCollidable() {
        return true;
    }*/
}
