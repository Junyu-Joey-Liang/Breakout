package breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static breakout.Constants.*;


/**
 * A Breakout game using Javafx.
 *
 * @author Junyu Liang
 */
public class Breakout extends Application {
    private Scene myScene;
    private Group myRoot;
    private Stage myStage;
    private Animation myAnimation;
    private AudioClip myAudioClip;
    private Bouncer myBouncer;
    private Paddle myPaddle;
    private BrickList myBrickList;
    private ArrayList<PowerUp> myPowerUpList;
    private int currentLive;
    private int currentLevel;
    private Label myLifeLabel;
    private Label myLevelLabel;
    private Label myScoreLabel;
    private int score;
    private boolean paused = false;
    private boolean challenge;


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start(Stage stage) {
        myScene = setupStartScene(WINDOW_WIDTH, WINDOW_HEIGHT, START_BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        myStage = stage;
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myAnimation = animation;
        myAudioClip = new AudioClip(getClass().getClassLoader().getResource(SOUND_EFFECT).toString());
        animation.play();

    }

    private Scene setupGameScene(int width, int height, int level) {
        var root = new Group();
        var scene = new Scene(root, width, height, GAME_BACKGROUND[level - 1]);
        var bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Bouncer(bouncerImage);
        root.getChildren().add(myBouncer.getView());
        var paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new Paddle(paddleImage, PADDLE_SPEED);
        root.getChildren().add(myPaddle.getView());
        myBrickList = new BrickList();
        myBrickList.create(level);
        for (Brick b : myBrickList.getMyList()) {
            root.getChildren().add(b.getView());
        }
        myPowerUpList = new ArrayList<>();
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

        if (level == 1) currentLive = 3;
        myLifeLabel = new Label("Current lives: " + currentLive);
        myLifeLabel.setAlignment(Pos.TOP_LEFT);
        root.getChildren().add(myLifeLabel);

        myLevelLabel = new Label("Current level: " + currentLevel);
        myLevelLabel.setLayoutX(190);
        root.getChildren().add(myLevelLabel);
        myRoot = root;

        myScoreLabel = new Label("Score: " + score);
        myScoreLabel.setLayoutX(400);
        root.getChildren().add(myScoreLabel);
        myRoot = root;

        return scene;
    }

    private Scene setupStartScene(int width, int height, Paint background) {
        var root = new Group();
        var scene = new Scene(root, width, height, background);

        currentLevel = 0;
        currentLive = 3;
        Button start = new Button();
        start.setLayoutX(240);
        start.setLayoutY(80);
        start.setFont(new Font(20));
        start.setText("Start");
        start.setOnAction(value -> {
            challenge = false;
            currentLevel = 1;
            score = 0;
            currentLive = 3;
            myScene = setupGameScene(WINDOW_WIDTH, WINDOW_HEIGHT, 1);
            myStage.setScene(myScene);
        });
        root.getChildren().add(start);

        Button challengestart = new Button();
        challengestart.setLayoutX(240);
        challengestart.setLayoutY(130);
        challengestart.setFont(new Font(20));
        challengestart.setText("Challenge");
        challengestart.setOnAction(value -> {
            challenge = true;
            currentLevel = 1;
            score = 0;
            currentLive = 3;
            myScene = setupGameScene(WINDOW_WIDTH, WINDOW_HEIGHT, 1);
            myStage.setScene(myScene);
        });
        root.getChildren().add(challengestart);
        Text ruletext = new Text(20, 200, RULES);
        ruletext.setFill(Color.WHITE);
        root.getChildren().add(ruletext);

        Text nametext = new Text(180, 50, "BREAKOUT");
        nametext.setFill(Color.WHITE);
        nametext.setFont(new Font(40));
        root.getChildren().add(nametext);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        myRoot = root;
        return scene;
    }

    private Scene setupEndScene(int width, int height, Paint background, boolean win) {
        var root = new Group();
        var scene = new Scene(root, width, height, background);
        currentLevel = 4;
        Button restart = new Button();
        restart.setLayoutX(220);
        restart.setLayoutY(260);
        restart.setText("Home");
        restart.setFont(new Font(20));
        restart.setOnAction(value -> {
            myScene = setupStartScene(WINDOW_WIDTH, WINDOW_HEIGHT, START_BACKGROUND);
            myStage.setScene(myScene);
        });
        root.getChildren().add(restart);
        myScoreLabel = new Label("Final Score: " + score);
        myScoreLabel.setLayoutX(250);
        myScoreLabel.setFont(new Font(15));
        root.getChildren().add(myScoreLabel);

        Label resultLabel;
        if (win) resultLabel = new Label("YOU WON!");
        else resultLabel = new Label("YOU LOST!");
        resultLabel.setFont(new Font(40));
        resultLabel.setLayoutX(190);
        resultLabel.setLayoutY(160);
        root.getChildren().add(resultLabel);
        myRoot = root;
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void step(double elapsedTime) {
        if (paused) return;
        changeLevel();
        updateCollision(elapsedTime);
        powerUpsUpdate(elapsedTime);
    }

    private void pauseOrResume() {
        paused = !paused;
        if (paused) myAnimation.pause();
        else myAnimation.play();
    }

    private void changeLevel() {
        if (currentLive <= 0 && currentLevel != 4) {
            myScene = setupEndScene(WINDOW_WIDTH, WINDOW_HEIGHT, LOST_BACKGROUND, false);
            myStage.setScene(myScene);
        } else if (myBrickList != null && myBrickList.getMyList().size() == 0) {
            if (currentLevel == 3) {
                myScene = setupEndScene(WINDOW_WIDTH, WINDOW_HEIGHT, WIN_BACKGROUND, true);
                myStage.setScene(myScene);
            } else if (currentLevel == 1 || currentLevel == 2) {
                currentLevel++;
                myScene = setupGameScene(WINDOW_WIDTH, WINDOW_HEIGHT, currentLevel);
                myStage.setScene(myScene);
            }
        }
        if (currentLevel > 0 && currentLevel < 4)
            myLevelLabel.textProperty().setValue("Current level: " + currentLevel);
    }

    private void jumpLevel(int i) {
        currentLevel = i;
        myScene = setupGameScene(WINDOW_WIDTH, WINDOW_HEIGHT, currentLevel);
        myStage.setScene(myScene);
        if (currentLevel > 0 && currentLevel < 4)
            myLevelLabel.textProperty().setValue("Current level: " + currentLevel);
    }


    private void updateCollision(double elapsedTime) {
        if (currentLevel == 0 || currentLevel == 4) return;
        if (myBouncer == null) return;
        updateBouncerLocation(elapsedTime);
        paddleWithBouncer();
        bricksWithBouncer(elapsedTime);
    }

    private void updateBouncerLocation(double elapsedTime){
        if (currentLevel == 0 || currentLevel == 4) return;
        myBouncer.getView().setX(myBouncer.getView().getX() + myBouncer.getXVel() * elapsedTime);
        myBouncer.getView().setY(myBouncer.getView().getY() + myBouncer.getYVel() * elapsedTime);
    }

    private void paddleWithBouncer() {
        if (currentLevel == 0 || currentLevel == 4) return;
        if (myBouncer.getView().getX() <= 0 || myBouncer.getView().getX() >= (myScene.getWidth() - myBouncer.getView().getBoundsInLocal().getWidth())) myBouncer.reverseX();
        if (myBouncer.getView().getY() <= 0) myBouncer.reverseY();
        if (myBouncer.getView().getBoundsInParent().intersects(myPaddle.getView().getBoundsInParent())) {
            myBouncer.reverseY();
        } else if (myBouncer.getView().getY() >= (myScene.getHeight() - myBouncer.getView().getBoundsInLocal().getWidth())) {
            resetBouncerAndPaddle();
            currentLive -= 1;
            myLifeLabel.textProperty().setValue("Current lives: " + currentLive);
        }
    }

    private void bricksWithBouncer(double elapsedTime) {
        if (currentLevel == 0 || currentLevel == 4) return;
        Bounds myBouncerBounds = myBouncer.getView().getBoundsInParent();
        Bounds brickBounds;
        for (int i = 0; i < (myBrickList.getMyList().size()); i++) {
            myBrickList.getMyList().get(i).update(elapsedTime);
            brickBounds = myBrickList.getMyList().get(i).getView().getBoundsInParent();
            if (myBouncerBounds.intersects(brickBounds)) {
                myAudioClip.play();
                if (myBouncer.getYVel() > 0 && myBouncerBounds.getMaxY() >= brickBounds.getMinY()) {
                    myBouncer.reverseY();
                } else if (myBouncer.getYVel() < 0 && myBouncerBounds.getMinY() <= brickBounds.getMaxY()) {
                    myBouncer.reverseY();
                }
                if (myBouncer.getXVel() > 0 && myBouncerBounds.getMaxX() >= brickBounds.getMinX()) {
                    myBouncer.reverseX();
                } else if (myBouncer.getXVel() < 0 && myBouncerBounds.getMinX() <= brickBounds.getMaxX()) {
                    myBouncer.reverseX();
                }
                if (myBrickList.getMyList().get(i).reduceLife() == 0) {
                    score += myBrickList.getMyList().get(i).getBrickPoint();
                    myScoreLabel.textProperty().setValue("Score: " + score);
                    if (myBrickList.getMyList().get(i).getWithPowerUp()) {
                        initializePowerUp(myBrickList.getMyList().get(i));
                    }
                    myRoot.getChildren().remove(myBrickList.getMyList().get(i).getView());
                    myBrickList.getMyList().remove(myBrickList.getMyList().get(i));
                }
            }
        }
    }

    private void powerUpsUpdate(double elapsedTime) {
        if (currentLevel == 0 || currentLevel == 4) {
            return;
        }
        for (int i = 0; i < myPowerUpList.size(); i++) {
            myPowerUpList.get(i).update(elapsedTime);
            if (myPowerUpList.get(i).getPowerUpView().getBoundsInParent().intersects(myPaddle.getView().getBoundsInParent())) {
//                System.out.println("Powerup caught");
                doPowerUp(myPowerUpList.get(i));
                myRoot.getChildren().remove(myPowerUpList.get(i).getPowerUpView());
                myPowerUpList.remove(i);
            } else if (myPowerUpList.get(i).getPowerUpView().getCenterY() > WINDOW_HEIGHT - POWER_UP_SIZE) {
                myRoot.getChildren().remove(myPowerUpList.get(i).getPowerUpView());
                myPowerUpList.remove(i);
            }
        }
    }

    private void doPowerUp(PowerUp powerUp) {
        PowerUpType type = powerUp.getType();

        if (type == PowerUpType.STRETCH_PADDLE) {
            System.out.println("Powerup: extend paddle");
            myPaddle.getView().setFitWidth(myPaddle.getView().getFitWidth() * 1.4);
        } else if (type == PowerUpType.SLOWDOWN_BOUNCER) {
            myBouncer.setYVel(myBouncer.getYVel() * 0.7);
            myBouncer.setXVel(myBouncer.getXVel() * 0.7);
            System.out.println("Powerup: slow down bouncer");
        } else if (type == PowerUpType.EXTRA_LIFE) {
            System.out.println("Powerup: extra life");
            addLife();
        }
    }

    private void initializePowerUp(Brick brick) {
        PowerUp powerUp = new PowerUp(brick.getView().getX(), brick.getView().getY());
        myPowerUpList.add(powerUp);
        myRoot.getChildren().add(powerUp.getPowerUpView());
    }

    private void resetBouncerAndPaddle() {
        myRoot.getChildren().remove(myBouncer.getView());
        var bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Bouncer(bouncerImage);
        myRoot.getChildren().add(myBouncer.getView());

        myRoot.getChildren().remove(myPaddle.getView());
        var paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new Paddle(paddleImage, PADDLE_SPEED);
        myRoot.getChildren().add(myPaddle.getView());
    }

    private void handleKeyInput(KeyCode code) {
        if (challenge) {
            if (code == KeyCode.RIGHT) code = KeyCode.LEFT;
            else if (code == KeyCode.LEFT) code = KeyCode.RIGHT;
        }
        if (code == KeyCode.RIGHT && myPaddle.getView().getBoundsInParent().getMaxX() <= myScene.getWidth())
            myPaddle.moveRight();
        if (code == KeyCode.LEFT && myPaddle.getView().getBoundsInParent().getMinX() >= 0) myPaddle.moveLeft();
        if (code == KeyCode.L) {
            addLife();
        }
        if (code == KeyCode.DIGIT1) {
            jumpLevel(1);
        }
        if (code == KeyCode.DIGIT2) {
            jumpLevel(2);
        }
        if (code == KeyCode.DIGIT3) {
            jumpLevel(3);
        }
        if (code == KeyCode.SPACE) {
            pauseOrResume();
        }
    }

    private void handleMouseInput(double x, double y) {
    }

    private void addLife() {
        if (currentLevel == 0 || currentLevel == 4) return;
        currentLive++;
        myLifeLabel.textProperty().setValue("Current lives: " + currentLive);
    }

    /**
     * Start the program.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
