package utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {

    // method used for retrieving images from the Resources of the project
    public static BufferedImage getResourceImg(String path)
    {
        BufferedImage resourceImg = null;

        try
        {
            resourceImg = ImageIO.read(new File("src/" + path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Image not found.");
        }

        return resourceImg;
    }
}
