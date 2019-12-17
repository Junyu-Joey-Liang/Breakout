package breakout;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * Constants for the game.
 */
public class Constants {
    // game
    public static final String TITLE = "Breakout";
    public static final int WINDOW_HEIGHT = 400;
    public static final int WINDOW_WIDTH = 594;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint[] GAME_BACKGROUND = {Color.HONEYDEW, Color.LIGHTCYAN, Color.LAVENDER};
    public static final Paint START_BACKGROUND = Color.BLACK;
    public static final Paint WIN_BACKGROUND = Color.AZURE;
    public static final Paint LOST_BACKGROUND = Color.LIGHTSALMON;
    public static final String SOUND_EFFECT = "pong_beep.wav";

    // paddle
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final double PADDLE_SPEED = 40;
    public static final double PADDLE_HEIGHT = 0.04 * WINDOW_HEIGHT;
    public static final double PADDLE_WIDTH = 0.2 * WINDOW_WIDTH;

    // bouncer
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final double BOUNCER_SPEED = 220;
    public static final double BOUNCER_START_Y = WINDOW_HEIGHT - PADDLE_HEIGHT - 50;
    public static double BOUNCER_SIZE = 0.04 * Math.min(WINDOW_HEIGHT, WINDOW_WIDTH);

    // bricks
    public static final int BRICK_WIDTH = 40;
    public static final int BRICK_HEIGHT = 25;
    public static final int BRICK_SPACING = 26;
    public static final String BASIC_BRICK = "brick1.gif";
    public static final String TOUGH_BRICK = "brick2.gif";
    public static final int START_X = 13;
    public static final int START_Y = 30;
    public static final int NUM_OF_COLS = 9;
    public static final int NUM_OF_ROWS = 3;
    public static final double BRICK_VEL = 30;
    public static final double FRACTION_WITH_POWERUP = 0.5;

    // start window
    public static final String RULES = "1. Control the paddle using left and right arrow. \n2. You lose a life if the " +
            "ball touches the bottom edge\n3.Try to hit all the bricks\n    Blue brick: crashed after one hit\n    Green " +
            "brick: crashed after two hits\n4.Powerups may fall when a brick is crashed, catch them with the paddle to " +
            "use power ups\n5.Cheat keys:\n    [1-3] switch to level\n    [L] add life\n    [SPACE] pause and resume\n" +
            "6. In challenge mode, the paddle will go in the opposite direction as you indicate.";

    // power up
    public static final double POWER_UP_SIZE = 5;
    public static final double POWER_UP_SPEED = 60;
}
