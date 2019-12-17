package breakout;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static breakout.Constants.POWER_UP_SIZE;
import static breakout.Constants.POWER_UP_SPEED;

/**
 * Class for all the power up types.
 */
public class PowerUp {
    private final PowerUpType type;
    private final Circle powerUpView;


    /**
     * Constructor for power up ball.
     *
     * @param x initial x location of powerup
     * @param y initial y location of pwoerup
     */
    public PowerUp(double x, double y) {
        powerUpView = new Circle(x, y, POWER_UP_SIZE);
        double r = Math.random();
        if (r < 0.3) type = PowerUpType.EXTRA_LIFE;
        else if (r < 0.6) type = PowerUpType.SLOWDOWN_BOUNCER;
        else {
            type = PowerUpType.STRETCH_PADDLE;
        }
        setColor();
    }

    /**
     * Update the position of the power up ball.
     *
     * @param elapsedTime time interval from last update.
     */
    public void update(double elapsedTime) {
        getPowerUpView().setCenterY(elapsedTime * POWER_UP_SPEED + getPowerUpView().getCenterY());
    }

    /**
     * Retrieve view of power up ball.
     *
     * @return imageview of power up ball
     */
    public Circle getPowerUpView() {
        return powerUpView;
    }

    /**
     * Retrieve type of power up.
     *
     * @return type of powerup
     */
    public PowerUpType getType() {
        return type;
    }

    private void setColor() {
        if (this.type == PowerUpType.EXTRA_LIFE) this.powerUpView.setFill(Color.PINK);
        if (this.type == PowerUpType.SLOWDOWN_BOUNCER) this.powerUpView.setFill(Color.BLUE);
        if (this.type == PowerUpType.STRETCH_PADDLE) this.powerUpView.setFill(Color.ORANGE);
    }


}
