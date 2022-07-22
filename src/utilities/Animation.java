package utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Animation {

    private ArrayList<BufferedImage> frameList; // list of all the frames of the animation
    private int frameIndex = 0;
    private long prevTime;  // the last time at which a frame has changed
    private int deltaTime;  // the time it takes for a frame to change

    public Animation(int deltaTime)
    {
        this.deltaTime = deltaTime;
        frameList = new ArrayList<>();
    }

    // method that adds a frame(of type BufferedImage) to the frame list
    public void addFrame(BufferedImage frame)
    {
        frameList.add(frame);
    }

    // method that returns a frame
    public BufferedImage getFrame()
    {
        if (!frameList.isEmpty())
        {
            return frameList.get(frameIndex);
        }

        return null;
    }

    // method that updates the frames in order to create the animation
    public void updateFrame()
    {
        // if the difference between the current time and the last time a frame changed is greater than the delta time
        if (System.currentTimeMillis() - prevTime > deltaTime)
        {
            frameIndex++;  // the frame is updated

            // once the frame index is beyond the total number of frames
            if (frameIndex >= frameList.size())
                frameIndex = 0;   // we set it back to 0 (the first frame in the list)

            prevTime = System.currentTimeMillis();
        }
    }
}
