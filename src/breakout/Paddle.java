package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static breakout.Constants.*;

public class Paddle {
    public final double PADDLE_SPEED;

    private ImageView myView;

    /**
     * Constructor class for paddle.
     *
     * @param paddleImage image of paddle
     * @param speed initial speed of paddle in x direction
     */
    public Paddle(Image paddleImage, Double speed) {
        myView = new ImageView(paddleImage);
        this.PADDLE_SPEED = speed;
        myView.setFitHeight(PADDLE_HEIGHT);
        myView.setFitWidth(PADDLE_WIDTH);
        myView.setX(WINDOW_WIDTH / 2 - PADDLE_WIDTH / 2);
        myView.setY(WINDOW_HEIGHT - PADDLE_HEIGHT);
    }

    /**
     * Retrieve paddle view.
     *
     * @return imageview of the paddle
     */
    public ImageView getView() {
        return myView;
    }

    /**
     * Move the paddle to the right.
     */
    public void moveRight() {
        myView.setX(myView.getX() + PADDLE_SPEED);
    }

    /**
     * Move the paddle to the left.
     */
    public void moveLeft() {
        myView.setX(myView.getX() - PADDLE_SPEED);
    }


}
