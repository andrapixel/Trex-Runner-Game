package view;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameView extends JFrame {

    private GamePanel gamePanel;
    public final static int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 300;    // constants for the window width and height sizes

    public GameView(String text)
    {
        super("T-Rex Runner Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);    // centers the window in the middle of the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gamePanel = new GamePanel(text);
        add(gamePanel);
        addKeyListener(gamePanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);

                gamePanel.savePlayerData();  // if the game window is closed, the data about the last game will be saved
                MenuView menuView = new MenuView();
            }
        });

        setVisible(true);  // create the gaming window and make it visible
        startGame();
    }

    public void startGame()
    {
        gamePanel.startThread();
    }
}
