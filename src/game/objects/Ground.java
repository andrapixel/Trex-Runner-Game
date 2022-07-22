package game.objects;

import utilities.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static view.GamePanel.GROUND;
import static view.GameView.WINDOW_WIDTH;

public class Ground {

    private class GroundImage {
        double x;   // the x coordinate of the ground object
        BufferedImage image;
    }

    private BufferedImage ground1, ground2, ground3;   // there are 3 types of grounds that can be generated
    private ArrayList<GroundImage> imgList;

    public Ground()
    {
        imgList = new ArrayList<GroundImage>();

        ground1 = Resources.getResourceImg("resources/original/ground1.png");
        ground2 = Resources.getResourceImg("resources/original/ground2.png");
        ground3 = Resources.getResourceImg("resources/original/ground3.png");

        // we imagine that the default size of an image is the size of the 3rd ground image (because that is the smallest
        // out of the three - I failed at cropping them equally :) and it SHOWS)
        int imgSize = WINDOW_WIDTH / ground3.getWidth() + 2;   // the amount ground-images that fit onto the screen

        // generate images of the random types of ground and add them to the image list
        for (int i = 0; i < imgSize; ++i)
        {
            GroundImage gi = new GroundImage();
            gi.x = i * ground3.getWidth();
            setGroundImage(gi);
            imgList.add(gi);
        }
    }

    public void updateGround()
    {
        Iterator<GroundImage> it = imgList.iterator();
        GroundImage firstPart = it.next();
        // by decreaing the x coordinate, we create the illusion that the ground is moving (and combined with the movement
        // of the trex's legs, we get the illusion that the trex is running continuously)
        firstPart.x--;
        double prevX = firstPart.x;

        // while there are still images in the list, we keep updating their x-positions
        while (it.hasNext())
        {
            GroundImage img = it.next();
            img.x = prevX + ground3.getWidth();
            prevX = img.x;
        }

        // if the first image of the ground in the list is greater than its width(the first image is out of the screen)
        if (firstPart.x < -ground3.getWidth())
        {
            imgList.remove(firstPart);  // we remove the image from the list
            firstPart.x = prevX + ground3.getWidth();
            setGroundImage(firstPart);  // and add a new one
            imgList.add(firstPart);
        }
    }

    // method that generates a random type of ground out of the 3 that are available
    private int generateTypeOfGround()
    {
        Random rand = new Random();
        int type = rand.nextInt(3);

        switch (type) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                return 1;
        }
    }

    // assign a ground image to its specific type
    private void setGroundImage(GroundImage ground)
    {
        int groundType = generateTypeOfGround();

        switch (groundType) {
            case 1:
                ground.image = ground1;
                break;
            case 2:
                ground.image = ground2;
                break;
            case 3:
                ground.image = ground3;
                break;
        }
    }

    // methods that draws the ground
    public void drawGround(Graphics graphics)
    {
        for (GroundImage gi : imgList)
            graphics.drawImage(gi.image, (int)gi.x, (int)GROUND - 13, null);
    }
}
