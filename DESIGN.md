Design
====

This project implements the game of Breakout. The game is modeled after the traditional Breakout games. Also, some parts of the code were based on  lab_bounce.java by Robert Duvall.

Name: Junyu Liang
____

## What are the project's design goals, specifically what kinds of new features did you want to make easy to add

The project is designed so that each component of the game (bouncer, paddle, bricks, and power ups) are separated and maintained in their own classes. This would make it easier to add in variations for each type. For example, if you would like to add a new type of brick, all you need to do is change the ``Brick`` class so that the new brick's features is initialized in the constructor, and updates correspondingly in the ``update()`` method.

Also, all the static constants for the game is contained in the ``Constants`` class. This design makes it very easy to navigate and make changes to different constants in the game. 

____

## Describe the high-level design of your project, focusing on the purpose and interaction of the core classes
The game is divided into 6 core classes. 


The main class is the ``Breakout`` class, which is the central control of the game flow. It is responsible for setup, game play, transition screens, updating the view and game stats (score, and health), and managing user inputs (keyboard and mouse).
The main class contains
* Object instances for game control & display (JavaFx classes)

    ``myScene``,``myStage``, ``myAnimation``, ``myAudioClip``, and labels for display
* Object instances of the game objects

    ``myBouncer``,``myPaddle``, ``myBrickList``, ``myPowerUpList``
* Private variable to keep track of the game 

    ``score``,``currentLive``, ``currentLevel``

The class would constantly call update methods within the instance objects to update position & game control for each game objects.


Below is the other core classes explained:
* ``Bouncer``: class that represents the bouncer of the game, which contains the image view of the bouncer, and x and y velocities of the bouncer. The main methods are ``reverseX()`` and ``reverseY()``, which reverses the directions of the bouncer
* ``Paddle``: class that represents the paddle. Contains the image view and the speed of the paddle. Main methods include ``moverLeft()`` and ``moveRight()``
* ``BrickList``: contains list of ``Brick`` instances currently in the game. Includes a ``create()`` method that takes in the level number, and create the corresponding brick list for that level
* ``Brick``: class that represents the brick. Stores the brick type, image view, whether the brick contains power up. The main methods include an ``update()`` method for updating the position of moving bricks, a ``clone()`` method which would return a cloned brick in the location specified (makes it easy to create repetitive bricks), a ``getPoints()`` method to retrieve the number of points you earn for destroying this brick.
* ``PowerUp``: class that represents the power up balls. Maintains the type of power up, and the image view of the ball. Has a ``update()`` method to move the power up ball.


____
##  What assumptions or decisions were made to simplify your project's design, especially those that affected adding required features
1. I decided to address all types of bricks in one class. I think for the sake of this project, I only have four types of bricks, and they share a lot of common methods, except for one or two lines of code. Therefore, I decided that creating an abstract brick class, and extending to each type would be an overhead
2. I decided to put the method that implements the power up actions (extend paddle, slow down paddle, extra life) in the main class ``Breakout``. Theoretically, it may look more clean if these methods are contained within the ``Powerup`` class itself. However, all the power up actions needs to access objects belonging to the main class, therefore, I decided that it would be more neat and concise code-wise to put them in main class. 





___
## describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline

I completed adding all the features in my plan to my project. But here are way to 

1. add new types of brick
    1. add a enum value to the Enum class ``BrickType``
    2. change the constructor of ``Brick`` class to initialize parameters to suit this new brick type if requested
    3. change the update() and getPoints() method in ``Brick`` class to add the actions for this new type of brick
    
2. add new level
    1. change ``changeLevel()`` method in ``BreakOut`` so that after level 3, the scene changes to a new level.
    2. add lines to initialize the new level bricks setup in ``Bricklist`` class's ``create()`` method.
    
3. add new features to paddle
    1. change the ``Paddle`` class, and maybe update the ``step()`` method in ``Breakout`` to update new feature actions for the paddle.
    
4. add new power ups (similar to adding new bricks)
    1. add a enum value to the Enum class ``PowerUpType``
    2. change the constructor of ``PowerUp`` class to initialize parameters for the view of this new type of power up
    3. change ``powerUpsUpdate()`` and ``doPowerUp()`` to do corresponding actions if this power up was caught.
    
    
        
    