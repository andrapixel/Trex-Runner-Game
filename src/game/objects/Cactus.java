package game.objects;

import utilities.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cactus extends Obstacle {

    private int x, y;  // coordinates of the position of the cactus
    private Rectangle cactusRect; // rectangle that determines the bounds of the cactus
    private BufferedImage cactusImg;

    private Trex trex;
    private boolean scoreWasAssigned = false;

    public Cactus(Trex trex)
    {
        this.trex = trex;
        cactusRect = new Rectangle();
        y = 165;  // the y position of the cactuses is stable
    }

    // Getters & Setters region
    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setCactusImg(BufferedImage cactusImg)
    {
        this.cactusImg = cactusImg;
    }
    // end region


    // Overriding Obstacle methods
    @Override
    public Rectangle getObjectBounds()
    {
        return cactusRect;
    }

    @Override
    public void drawObstacle(Graphics graphics)
    {
        graphics.drawImage(cactusImg, x, y, cactusImg.getWidth() / 2 , cactusImg.getHeight() / 2,
                null);
    }

    @Override
    public void updateObstaclePos()
    {
        x -= 2;

        cactusRect.x = this.x;
        cactusRect.y = this.y;
        cactusRect.width = cactusImg.getWidth() / 2;
        cactusRect.height = cactusImg.getHeight() / 2;
    }

    @Override
    public boolean obstacleIsOutOfBounds()
    {
        if (x + cactusImg.getWidth()/2 < 0)
            return true;

        return false;
    }

    @Override
    public boolean obstaclePassed()
    {
        if (trex.getX() > x)   // checks if the trex passed the cactus
            return true;

        return false;
    }

    @Override
    public boolean scoreWasAssigned()
    {
        return scoreWasAssigned;
    }

    @Override
    public void setScoreAssignation(boolean scoreWasAssigned)
    {
        this.scoreWasAssigned = scoreWasAssigned;
    }
}
