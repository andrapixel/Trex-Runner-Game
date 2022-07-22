package game.managers;

import game.objects.Cactus;
import game.objects.Obstacle;
import game.objects.Pterodactyl;
import game.objects.Trex;
import view.GamePanel;
import utilities.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static view.GameView.WINDOW_WIDTH;

public class ObstacleManager {

    private ArrayList<Obstacle> obstacleList;   // a list of all the obstacles that are being generated
    private Random randomObstacle;

    private Trex trex;
    private GamePanel panel;
    private boolean intersects = false;  // is true if the trex and and obstacle intersect
    private boolean nextObstacleIsPterodactyl = false;  // is true if the next obstacle that appears on the screen is a
                                                        // pterodactyl

    private BufferedImage cactusImg1, cactusImg2, cactusImg3, cactusImg4, cactusImg5, cactusImg6;

    public ObstacleManager(Trex trex, GamePanel gPanel)
    {
        this.trex = trex;
        this.panel = gPanel;
        obstacleList = new ArrayList<>();

        cactusImg1 = Resources.getResourceImg("resources/original/cactus1.png");
        cactusImg2 = Resources.getResourceImg("resources/original/cactus2.png");
        cactusImg3 = Resources.getResourceImg("resources/original/cactus3.png");
        cactusImg4 = Resources.getResourceImg("resources/original/cactus4.png");
        cactusImg5 = Resources.getResourceImg("resources/original/cactus5.png");
        cactusImg6 = Resources.getResourceImg("resources/original/cactus6.png");

        // set the first obstacle
        getRandomObstacle();
    }

    // method that updates the obstacles
    public void updateObstacle()
    {
        // for each obstacle in the list, we update its x-position
        for (Obstacle obstacle : obstacleList)
        {
            obstacle.updateObstaclePos();

            // if the obstacle has been passed by the trex without collision, and the score was not yet assigned
            if (obstacle.obstaclePassed() && !obstacle.scoreWasAssigned() && !intersects)
            {
                // if obstacle was a pterodactyl, add 30 points
                if (nextObstacleIsPterodactyl)
                    panel.increaseScoreBy(30);
                else
                    panel.increaseScoreBy(25);  // otherwise add 25

                obstacle.setScoreAssignation(true);   // and set the score as having been assigned
            }

            // if the rectangle of the trex and of the obstacle intersect(if the trex and obstacle have a collision)
            if (obstacle.getObjectBounds().intersects(trex.getObjectBounds()))
            {
                intersects = true;
                trex.setDead(true);     // then the trex dies
            }
        }

        Obstacle firstObstacle = obstacleList.get(0);
        // if the first obstacle in the list is out of the screen bounds
        if (firstObstacle.obstacleIsOutOfBounds())
        {
            obstacleList.remove(firstObstacle); // remove it and add a new one to the list
            getRandomObstacle();
        }
    }

    // draws the obstacles from the list
    public void drawObstacle(Graphics graphics)
    {
        for (Obstacle obstacle : obstacleList)
            obstacle.drawObstacle(graphics);
    }

    // method that returns a random cactus
    private Cactus getRandomCactus()
    {
        Random randomCactus = new Random();
        int cactusType = randomCactus.nextInt(6);   // there are 6 types of cactus-obstacles
        Cactus newCactus = new Cactus(trex);
        newCactus.setX(WINDOW_WIDTH);

        switch (cactusType) {
            case 0:
                newCactus.setCactusImg(cactusImg1);
                break;
            case 1:
                newCactus.setCactusImg(cactusImg2);
                break;
            case 2:
                newCactus.setCactusImg(cactusImg3);
                break;
            case 3:
                newCactus.setCactusImg(cactusImg4);
                newCactus.setY(150);    // for these images we need to set a different y-coordinate because of their size
                break;                  // so that it would look natural compared to the ground and sky
            case 4:
                newCactus.setCactusImg(cactusImg5);
                newCactus.setY(150);
                break;
            case 5:
                newCactus.setCactusImg(cactusImg6);
                newCactus.setY(150);
                break;
        }

        return newCactus;
    }

    // method that returns a random pterodactyl
    private Pterodactyl getRandomPterodactyl()
    {
        Random randomPos = new Random();
        Pterodactyl newPterodactyl = new Pterodactyl(trex);
        newPterodactyl.setX(WINDOW_WIDTH);
        // setting a random position of the pterodactyl on the sky
        newPterodactyl.setY(randomPos.nextInt(100, 165));

        return newPterodactyl;
    }

    // method that resets all the obstacles (called when the game ends)
    public void resetObstacles()
    {
        obstacleList.clear();
        getRandomObstacle();
        intersects = false;
    }

    // function that randomizes whether the next obstacle will be a cactus or a pterodactyl
    private void getRandomObstacle()
    {
        randomObstacle = new Random();
        int nextObstacle = randomObstacle.nextInt(10);

        // Note: in the original game, the pterodactyls appear much less than the cacti
        // since the pterodactyl can appear in 1/10 cases, and the generated numbers are
        // randomized, there will be a 10% probability for the pterodactyl to appear
        switch(nextObstacle) {
            case 5:
                obstacleList.add(getRandomPterodactyl());
                nextObstacleIsPterodactyl = true;
                break;
            default:
                obstacleList.add(getRandomCactus());
                nextObstacleIsPterodactyl = false;
                break;
        }
    }
}
