package game.objects;

import utilities.Resources;
import utilities.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

import static view.GamePanel.*;


public class Trex {

    private double x = 20, y = 100;    // x, y - coordinates of the initial position of the trex when the window opens
    private double speedY = 0;  // the speed with which our object(the T-Rex) jumps

    private BufferedImage trexJumpingIcon;
    private BufferedImage trexDeadIcon;
    private Animation trexRunningAnimation, trexDuckingAnimation; // when the trex is running/ducking, we need to animate
                                                                  // the movement of its legs
    private Rectangle trexRectUp, trexRectDown; // the rectangles that determine the bounds of the trex when it is standing
    // (when jumping, running, when standing or when it is dead -> trexRectUp) and when it is ducking -> trexRectDown
    private boolean isDead = false, isDown = false;

    public Trex()
    {
        // creating the rectangles that surround the trex images
        trexRectUp = new Rectangle();
        trexRectDown = new Rectangle();

        // creating the trex running animation
        trexRunningAnimation = new Animation(200);
        trexRunningAnimation.addFrame(Resources.getResourceImg("resources/original/trex1.png"));
        trexRunningAnimation.addFrame(Resources.getResourceImg("resources/original/trex2.png"));

        // creating an animation of the trex when it is ducking
        trexDuckingAnimation = new Animation(200);
        trexDuckingAnimation.addFrame(Resources.getResourceImg("resources/original/trexDown1.png"));
        trexDuckingAnimation.addFrame(Resources.getResourceImg("resources/original/trexDown2.png"));

        // retrieving the resources for the other trex states
        trexJumpingIcon = Resources.getResourceImg("resources/original/trexJump.png");
        trexDeadIcon = Resources.getResourceImg("resources/original/trexDead.png");
    }

    // Getters & Setters region
    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getSpeedY()
    {
        return speedY;
    }

    public void setSpeedY(double speedY)
    {
        this.speedY = speedY;
    }

    public boolean isDead()
    {
        return isDead;
    }

    public void setDead(boolean dead)
    {
        isDead = dead;
    }

    public boolean isDown()
    {
        return isDown;
    }

    public void setDown(boolean down)
    {
        isDown = down;
    }
    // end region

    // methods that draw the Trex on the panel in all its different states
    public void drawRunningTrex(Graphics graphics)
    {
        graphics.drawImage(trexRunningAnimation.getFrame(), (int)x, (int)y , trexRunningAnimation.getFrame().getWidth()/2,
                trexRunningAnimation.getFrame().getHeight()/2, null);
    }

    public void drawJumpingTrex(Graphics graphics)
    {
        graphics.drawImage(trexJumpingIcon, (int)x, (int)y, trexJumpingIcon.getWidth() / 2,
                trexJumpingIcon.getHeight() / 2, null);
    }

    public void drawDuckingTrex(Graphics graphics)
    {
        graphics.drawImage(trexDuckingAnimation.getFrame(), (int)x, (int)(y + 17), trexDuckingAnimation.getFrame().getWidth(),
                trexDuckingAnimation.getFrame().getHeight(), null);
    }

    public void drawDeadTrex(Graphics graphics)
    {
        graphics.drawImage(trexDeadIcon, (int)x, (int)y, trexDeadIcon.getWidth() / 2,
                trexDeadIcon.getHeight() / 2, null);
    }

    // method that redraws the trex and deals with the situations when the Trex is out of bounds(out of the window)
    public void updateTrexMovements()
    {
        // updating the animation frames
        trexRunningAnimation.updateFrame();
        trexDuckingAnimation.updateFrame();

        // if the Trex goes under the ground level
        if (y >= GROUND - trexRunningAnimation.getFrame().getHeight() / 2)
        {
            speedY = 0;
            y = GROUND - trexRunningAnimation.getFrame().getHeight() / 2;
        }
        else
        {
            speedY += GRAVITY;
            y += speedY;
        }

        // setting the upper bounds that the trex can reach when jumping
        if (y <= SKY)
            y = SKY;

        // configuring the rectangle sizes
        trexRectUp.x = (int)x;
        trexRectUp.y = (int)y;
        trexRectUp.width = trexJumpingIcon.getWidth() / 2;
        trexRectUp.height = trexJumpingIcon.getHeight() / 2;

        trexRectDown.x = (int)x;
        trexRectDown.y = (int)y;
        trexRectDown.width = trexDuckingAnimation.getFrame().getWidth();
        trexRectDown.height = trexDuckingAnimation.getFrame().getHeight();
    }

    // method that changes the position of the trex on the y-axis when jumping
    public void jump()
    {
        // for a jump (one hit of a space/arrow-up key) the trex's position on the y-axis diminishes by 3 pixels
        speedY = -3;
        y += speedY;
    }

    // method that returns the bounds of the trex object
    public Rectangle getObjectBounds()
    {
        if (isDown)
            return trexRectDown;

        return trexRectUp;
    }
}
