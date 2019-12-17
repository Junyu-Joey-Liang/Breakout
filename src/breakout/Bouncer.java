package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import static breakout.Constants.*;

/**
 * Bouncer class.
 */
public class Bouncer extends Circle {

    private double xVel;
    private double yVel;
    private ImageView imageView;

    /**
     * Constructor for bouncer.
     *
     * @param image bouncer image
     */
    public Bouncer(Image image) {
        imageView = new ImageView(image);
        imageView.setFitHeight(BOUNCER_SIZE);
        imageView.setFitWidth(BOUNCER_SIZE);
        imageView.setX(WINDOW_WIDTH / 2 - BOUNCER_SIZE / 2);
        imageView.setY(BOUNCER_START_Y);
        xVel = BOUNCER_SPEED;
        yVel = BOUNCER_SPEED;
    }

    /**
     * Set bouncer's x velocity.
     *
     * @param input new x velocity
     */
    public void setXVel(double input) {
        xVel = input;
    }

    /**
     * Set bouncer's y velocity
     *
     * @param input new y velocity
     */
    public void setYVel(double input) {
        yVel = input;
    }

    /**
     * Retrieve x velocity.
     *
     * @return current x vel
     */
    public double getXVel() {
        return xVel;
    }

    /**
     * Retrieve y velocity.
     *
     * @return current y vel
     */
    public double getYVel() {
        return yVel;
    }

    /**
     * Retrieve image view of bouncer.
     *
     * @return image view of bouncer
     */
    public ImageView getView() {
        return imageView;
    }

    /**
     * Reverse the bouncer in x direction.
     */
    public void reverseX() {
        xVel *= -1;
    }

    /**
     * Reverse the bouncer in y direction.
     */
    public void reverseY() {
        yVel *= -1;
    }

}
