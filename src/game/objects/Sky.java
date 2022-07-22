package game.objects;

import utilities.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static view.GameView.WINDOW_WIDTH;

public class Sky {

    private class Cloud {
        double x, y;    // the coordinates of the cloud
    }

    private BufferedImage cloudImg;
    private ArrayList<Cloud> cloudList;

    public Sky()
    {
        cloudImg = Resources.getResourceImg("resources/original/cloud.png");
        cloudList = new ArrayList<Cloud>();

        // generate 5 clouds positioned randomly at equal distances
        for (int i = 0; i < 5; ++i)
        {
            Cloud newCloud = new Cloud();
            newCloud.x = i * 2 * cloudImg.getWidth();
            generateRandomCloudPosition(newCloud);
        }
    }

    // method that adds a cloud with a random position on the y-axis to the list
    private void generateRandomCloudPosition(Cloud randCloud)
    {
        Random randomPos = new Random();
        randCloud.y = randomPos.nextDouble(20, 130);

        cloudList.add(randCloud);
    }

    // method that draws each cloud in the list
    public void drawClouds(Graphics graphics)
    {
        for (Cloud cloud : cloudList)
            graphics.drawImage(cloudImg, (int)cloud.x, (int)cloud.y, cloudImg.getWidth() / 2,
                    cloudImg.getHeight() / 2, null);
    }

    // method that updates the position of the clouds on the sky(changes their x coordinate to make it look like they're moving)
    public void updateSky()
    {
        Iterator<Cloud> it = cloudList.iterator();
        Cloud firstCloud = it.next();
        firstCloud.x -= 0.5f; // start shifting the position of the first cloud in the list

        // while there are still clouds in the list, we shift each one's position by 0.5f to the left of the screen
        while (it.hasNext())
        {
            Cloud nextCloud = it.next();
            nextCloud.x -= 0.5f;
        }

        // if the x coordinate of the first cloud in the list is bigger than the width of the cloud image (aka the cloud has
        // moved out of the screen bounds)
        if (firstCloud.x < -cloudImg.getWidth())
        {
            cloudList.remove(firstCloud);   // remove the cloud from the list
            firstCloud.x = WINDOW_WIDTH;    // set the x-pos of the cloud back to the right side of the window
            generateRandomCloudPosition(firstCloud);    // and add a new randomly-generated cloud to the list so that
                                                        // the cloud will never stop appearing on the sky
        }
    }
}
