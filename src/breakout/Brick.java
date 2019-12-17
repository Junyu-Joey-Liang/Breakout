package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static breakout.Constants.*;


/**
 * This class represents the bricks in the Breakout game. It keeps track of variables specific to the brick object,
 * including the current life count of the brick, dimensions, image view, and type specifications of the brick.
 * It also includes method to update positions for moving bricks, and some other minor methods such as getter and setters.
 * <p>
 * I think the class is well designed in a sense that it wraps the methods and variables specific to the brick game object.
 * That way, it is more clear for other classes to access and manipulate with the brick objects in the game.
 */
public class Brick {
    private final BrickType brickType;
    private int lives;
    private final ImageView imageView;
    private boolean withPowerUp;
    private boolean moveRight;

    /**
     * Constructor for new brick.
     *
     * @param brickImage image of brick
     * @param x          x location
     * @param y          y location
     * @param width      width of the brick
     * @param height     height of the brick
     * @param brickType  type of the brick
     * @param moveRight  if the brick moves, whether it moves right
     */
    public Brick(Image brickImage, double x, double y, double width, double height, BrickType brickType, boolean moveRight) {
        this.imageView = new ImageView(brickImage);
        this.brickType = brickType;
        this.moveRight = moveRight;
        this.setInitialLives(brickType);
        if (Math.random() < FRACTION_WITH_POWERUP) this.withPowerUp = true;
        else this.withPowerUp = false;
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setX(x);
        imageView.setY(y);
    }

    /**
     * Updates the brick's position if the brick is a moving brick type.
     *
     * @param elaspsedTime elasped time length from the last update
     */
    public void update(double elaspsedTime) {

        if (brickType == BrickType.MOVING_BASIC || brickType == BrickType.MOVING_TOUGH) {
            if (moveRight) {
                double new_x = imageView.getX() + elaspsedTime * BRICK_VEL;
                if (new_x > WINDOW_WIDTH) new_x = 0;
                imageView.setX(new_x);
            } else {
                double new_x = imageView.getX() - elaspsedTime * BRICK_VEL;
                if (new_x + BRICK_WIDTH < 0) new_x = WINDOW_WIDTH - BRICK_WIDTH;
                imageView.setX(new_x);
            }
        }
    }

    /**
     * Get number of points for destroying this brick.
     *
     * @return number of points you get if the brick is destroyed
     */
    public int getBrickPoint() {
        if (brickType == BrickType.BASIC) return 1;
        else if (brickType == BrickType.TOUGH) return 2;
        else if (brickType == BrickType.MOVING_BASIC) return 3;
        else return 4;
    }

    public ImageView getView() {
        return imageView;
    }

    public boolean getWithPowerUp() {
        return withPowerUp;
    }

    public int reduceLife() {
        lives--;
        return lives;
    }

    private void setInitialLives(BrickType brickType) {
        if (brickType == BrickType.BASIC || brickType == BrickType.MOVING_BASIC) this.lives = 1;
        if (brickType == BrickType.TOUGH || brickType == BrickType.MOVING_TOUGH) this.lives = 2;
    }
}

