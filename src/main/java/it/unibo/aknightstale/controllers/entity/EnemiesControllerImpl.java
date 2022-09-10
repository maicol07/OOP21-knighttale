package it.unibo.aknightstale.controllers.entity;

import it.unibo.aknightstale.controllers.MapControllerImpl;
import it.unibo.aknightstale.controllers.interfaces.EnemiesController;
import it.unibo.aknightstale.controllers.interfaces.MapController;
import it.unibo.aknightstale.models.Enemy;
import it.unibo.aknightstale.models.entity.Character;
import it.unibo.aknightstale.models.entity.factories.EntityFactory;
import it.unibo.aknightstale.utils.Point2D;
import it.unibo.aknightstale.views.entity.AnimatedEntityView;
import it.unibo.aknightstale.views.interfaces.MapView;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * The type Enemies controller.
 */
public class EnemiesControllerImpl implements EnemiesController {

    private int numEnemies = 0;
    private final EntityFactory factory;

    private final List<CharacterController<Character, AnimatedEntityView>> enemiesControllers;

    private final MapView mapView;
    private final MapController mapController;

    /**
     * Instantiates a new Enemies controller.
     *
     * @param numEnemies the num enemies
     * @param mapView    the map view
     * @param factory    the factory
     */
    public EnemiesControllerImpl(final int numEnemies, final MapView mapView, final EntityFactory factory, final MapController mapController) {
        enemiesControllers = new LinkedList<>();
        this.factory = factory;
        this.mapView = mapView;
        this.mapController = mapController;
        createEnemies(numEnemies);
    }

    public List<CharacterController<Character, AnimatedEntityView>> getEnemiesControllers() {
        return enemiesControllers;
    }

    /**
     * {@inheritDoc}
     */
    public void createEnemies(final int numEnemies) {
        this.numEnemies += numEnemies;

        for (int i = 0; i < this.numEnemies; i++) {
            final Point2D spawnPosition = this.mapController.getSpawnPosition();
            enemiesControllers.add(this.factory.getEnemy(spawnPosition.getX(), spawnPosition.getY()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawEnemies() {
        this.enemiesControllers.forEach((c) -> {
            switch (c.getModel().getDirection()) {
                case LEFT:
                    c.moveLeft();
                    break;
                case RIGHT:
                    c.moveRight();
                    break;
                case UP:
                    c.moveUp();
                    break;
                case DOWN:
                    c.moveDown();
                    break;
                default:
                    break;
            }
            //c.getView().update(c.getModel().getDirection());

            mapView.draw(c.getView(), c.getModel().getPosition().getX(), c.getModel().getPosition().getY());
            //gc.drawImage(c.getView().getImage(), c.getModel().getPosition().getX(), c.getModel().getPosition().getY());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDeadEnemies() {
        List<CharacterController<Character, AnimatedEntityView>> killedEnemies = new ArrayList<>();
        this.enemiesControllers.forEach(c -> {
            if (c.getModel().getHealth() == 0) {
                killedEnemies.add(c);
                this.factory.getEntityManager().removeEntity(c);
            }
        });
        enemiesControllers.removeAll(killedEnemies);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDirection(final Point2D playerPosition) {
        this.enemiesControllers.forEach(c -> {
            ((Enemy) c.getModel()).update(playerPosition);

            //(EnemyController<Enemy>)c.move(c.getModel().getDirection());

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void adaptPositionToScreenSize(final double traslX, final double traslY) {
        this.enemiesControllers.forEach(c -> {
            c.adaptPositionToScreenSize(traslX, traslY);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumEnemy() {
        return this.enemiesControllers.size();
    }
}
