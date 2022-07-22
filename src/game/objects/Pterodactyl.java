package game.objects;

import utilities.Animation;
import utilities.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Pterodactyl extends Obstacle {

    private int x, y;   // coordinates of the position of the pterodactyl
    private Rectangle pterodactylRect;  // rectagle that determines the bounds of the pterodactyl
    private Animation pterodactylAnimation;
    private Trex trex;
    private boolean scoreWasAssigned = false;

    public Pterodactyl(Trex trex)
    {
        // creating the pterodactyl flying-animation
        pterodactylAnimation = new Animation(200);
        pterodactylAnimation.addFrame(Resources.getResourceImg("resources/original/pterodactyl1.png"));
        pterodactylAnimation.addFrame(Resources.getResourceImg("resources/original/pterodactyl2.png"));

        pterodactylRect = new Rectangle();
        this.trex = trex;
    }

    // Getters & Setters region
    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    // end region


    // Overriding Obstacle methods
    @Override
    public Rectangle getObjectBounds()
    {
        return pterodactylRect;
    }

    @Override
    public void drawObstacle(Graphics graphics)
    {
        graphics.drawImage(pterodactylAnimation.getFrame(), x, y, pterodactylAnimation.getFrame().getWidth()/2,
                pterodactylAnimation.getFrame().getHeight()/2, null);
    }

    @Override
    public void updateObstaclePos()
    {
        x -= 2;
        pterodactylRect.x = x;
        pterodactylRect.y = y;
        pterodactylRect.width = pterodactylAnimation.getFrame().getWidth() / 2;
        pterodactylRect.height = pterodactylAnimation.getFrame().getHeight() / 2;
        pterodactylAnimation.updateFrame();
    }

    @Override
    public boolean obstacleIsOutOfBounds()
    {
        if (x + pterodactylAnimation.getFrame().getWidth()/2 < 0)
            return true;

        return false;
    }

    @Override
    public boolean obstaclePassed()
    {
        if (trex.getX() > x)  // if the trex's x pos is larger than the pterodactyl's x pos it means that
            return true;      // they have passed by each other

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
