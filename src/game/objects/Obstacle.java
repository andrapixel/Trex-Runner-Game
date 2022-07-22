package game.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Obstacle {

    public abstract Rectangle getObjectBounds();    // returns the bounds of the obstacle as a rectangle
    public abstract void drawObstacle(Graphics graphics);   // draws the obstacle
    public abstract void updateObstaclePos();   // updates the position of the obstacle in order for it to be redrawn
    public abstract boolean obstacleIsOutOfBounds();    // checks if an obstacle is out of the screen bounds
    public abstract boolean obstaclePassed();   // checks if the trex passed the given obstacle
    public abstract boolean scoreWasAssigned();    // returns true if the score was assigned to the player
    public abstract void setScoreAssignation(boolean scoreWasAssigned);     // scoreWasAssigned = true, if the score was
                                                                            // assigned and false otherwise
}
