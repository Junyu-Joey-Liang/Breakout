package breakout;

import javafx.scene.image.Image;

import java.util.ArrayList;

import static breakout.Constants.*;

/**
 * This class is used to create and maintain the active brick list in the game.
 * It keeps track of all the bricks in a array list of Brick objects,
 * and contains a method create() that creates the bricks for each level.
 * <p>
 * The class helps with the overall design since it encapsulates the bricks creation. This makes the code in the main method
 * (which calls the create() method in this class when switch to a new level) look much cleaner. Also, I chose to use a class
 * to maintain the brick list, instead of just a data structure in the main class because it makes adding features to the bricks
 * as a whole easier in the future. For example, if you want to turn all the bricks into a different type at some period of time
 * (maybe a possible power up), then you can just add a method in this class that changes all the bricks in the list, and call
 * it in the main class when needed.
 */
public class BrickList {
    private ArrayList<Brick> myList;
    private final Image basicImage = new Image(getClass().getClassLoader().getResourceAsStream(BASIC_BRICK));
    private final Image toughImage = new Image(getClass().getClassLoader().getResourceAsStream(TOUGH_BRICK));

    /**
     * Constructor for the brick list. Initializes an arraylist to store the active bricks in the game.
     */
    public BrickList() {
        myList = new ArrayList<>();
    }

    /**
     * Create the brick layout for the specific level and add to the brick list.
     *
     * @param level the level to create
     */
    public void create(int level) {
        if (level == 1) createLevelOne();
        else if (level == 2) createLevelTwo();
        else if (level == 3) createLevelThree();
    }

    /**
     * Retrieve the list of bricks.
     *
     * @return list of bricks
     */
    public ArrayList<Brick> getMyList() {
        return myList;
    }

    private void createLevelOne() {
        for (int i = 0; i < NUM_OF_COLS; i++) {
            createColumn(BrickType.BASIC, START_X + i * (BRICK_WIDTH + BRICK_SPACING), START_Y, NUM_OF_ROWS);
        }
    }

    private void createLevelTwo() {
        boolean flag = false;
        for (int i = 0; i < NUM_OF_COLS; i++) {
            if (flag) createColumn(BrickType.BASIC, START_X + i * (BRICK_WIDTH + BRICK_SPACING), START_Y, NUM_OF_ROWS);
            else createColumn(BrickType.TOUGH, START_X + i * (BRICK_WIDTH + BRICK_SPACING), START_Y, NUM_OF_ROWS);
            flag = !flag;
        }
    }

    private void createLevelThree() {
        boolean rightflag = false;
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            if (rightflag) createMovingRow(START_X, START_Y + i * (BRICK_HEIGHT + BRICK_SPACING), NUM_OF_COLS, true);
            else createMovingRow(START_X, START_Y + i * (BRICK_HEIGHT + BRICK_SPACING), NUM_OF_COLS, false);
            rightflag = !rightflag;
        }
    }

    private void createMovingRow(int x, int y, int count, boolean right) {
        boolean flag = false;

        for (int i = 0; i < count; i++) {
            if (flag)
                myList.add(new Brick(toughImage, x + i * (BRICK_WIDTH + BRICK_SPACING), y, BRICK_WIDTH, BRICK_HEIGHT, BrickType.MOVING_TOUGH, right));
            else
                myList.add(new Brick(basicImage, x + i * (BRICK_WIDTH + BRICK_SPACING), y, BRICK_WIDTH, BRICK_HEIGHT, BrickType.MOVING_BASIC, right));
            flag = !flag;
        }
    }

    private void createColumn(BrickType type, int x, int y, int count) {
        Image brickImage;
        if (type == BrickType.BASIC || type == BrickType.MOVING_BASIC) brickImage = basicImage;
        else brickImage = toughImage;

        for (int i = 0; i < count; i++) {
            myList.add(new Brick(brickImage, x, y + i * (BRICK_HEIGHT + BRICK_SPACING), BRICK_WIDTH, BRICK_HEIGHT, type, false));
        }
    }
}
