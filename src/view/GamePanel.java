package view;

import game.managers.ObstacleManager;
import game.managers.RankingManager;
import game.objects.*;
import utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static view.GameView.WINDOW_HEIGHT;
import static view.GameView.WINDOW_WIDTH;

// Note: we need to implement the Runnable Interface because this game runs for infinity, so we need to make the panel
// act like a thread
public class GamePanel extends JPanel implements Runnable, KeyListener {
    // constant declarations
    public static final double GRAVITY = 0.1f;
    public static final double GROUND = 200, SKY = 20;

    private Thread thread;
    private RankingManager rankingManager = RankingManager.getInstance();

    private Trex trex;
    private Ground groundObj;
    private Sky sky;
    private ObstacleManager obstacleManager;
    TrexState characterState = TrexState.INITIAL_STATE; // the state of the trex is set to INITIAL_STATE at the beginning of the game

    private int score = 0, highScore = 0;
    private String playerName = "";

    private BufferedImage gameOverImg, restartImg;
    private JButton restartBtn;


    public GamePanel(String playerName)
    {
        thread = new Thread(this);  // turn the panel into a thread

        this.playerName = playerName;

        // initializing the game objects
        trex = new Trex();
        groundObj = new Ground();
        sky = new Sky();
        obstacleManager = new ObstacleManager(trex, this);

        this.setLayout(new GridBagLayout());
        this.setAlignmentY(100);

        gameOverImg = Resources.getResourceImg("resources/original/gameOver.png");
        restartImg = Resources.getResourceImg("resources/original/restart2.png");
        restartBtn = new JButton(new ImageIcon(restartImg));

        showRestartButton();
    }

    public void startThread()
    {
        thread.start();
    }

    // Runnable Interface method overriding
    @Override
    public void run()
    {
        while (true)
        {
            trex.updateTrexMovements();

            if (trex.isDead())
                characterState = TrexState.DEAD;

            // if the Trex is dead, the game will end, which means that the restart button's visibility has to be set to true
            if (characterState == TrexState.DEAD)
            {
                restartBtn.setVisible(true);
                restartBtn.setEnabled(true);
            }

            // if the character state is either one besides the initial or dead states, it means the game is running
            // so all the game objects need to keep on being updated(redrawn)
            if (characterState != TrexState.INITIAL_STATE && characterState != TrexState.DEAD)
            {
                sky.updateSky();
                groundObj.updateGround();
                obstacleManager.updateObstacle();
                restartBtn.setVisible(false);
                restartBtn.setEnabled(false);
                restartBtn.setFocusable(false);
            }

            repaint();

            try
            {
                thread.sleep(5);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void restartGame()
    {
        savePlayerData();   // upon restarting the game, the data of the previously played game has to be saved
        restartBtn.setVisible(false);  // the restart button is made visible
        score = 0;   // the score is set back to 0
        trex.setDead(false);    // the trex is not dead anymore

        // setting the initial position of the trex and of the obstacles
        trex.setX(50);
        trex.setY(100);
        obstacleManager.resetObstacles();

        characterState = TrexState.INITIAL_STATE; // and the trex is brought back to its initial state
    }

    private void executeGame()
    {
        if (characterState == TrexState.DEAD)   // if the character is dead, restart the game
            restartGame();

        if (characterState == TrexState.INITIAL_STATE)  // after pressing the space bar for the first time to start playing
        {
            characterState = TrexState.JUMPING;     // the trex jumps and its position is shifted a bit
            trex.jump();
            trex.setX(50);
        }

        characterState = TrexState.JUMPING;
        trex.jump();
    }

    // KeyListener Interface method overriding
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            // dealing with the way the game responds when the space and arrow-up keys are pressed
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_KP_UP:
                executeGame();
                break;
            case KeyEvent.VK_KP_DOWN:   // when the arrow-down key is pressed, the trex ducks
                trex.setDown(true);
                characterState = TrexState.DUCKING;
                break;
            default:
                // if any other key is pressed the trex is killed, the game stops, and the following error message pops up
                characterState = TrexState.DEAD;
                String errorMsg = "\nSeems like you used the wrong controls :(\nKind reminder that you can only make the Trex move\nby " +
                        "using the Space/Arrow-Up keys for jumping,\nor the Arrow-Down key for ducking.";
                JOptionPane.showMessageDialog(new JFrame(), errorMsg, "Oops!", JOptionPane.ERROR_MESSAGE);
                restartBtn.setVisible(false);
                restartGame();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // when the space/arrow up keys are released, the trex keeps on running
        characterState = TrexState.RUNNING;

        // if the arrow-down key is released, the trex doesn't duck anymore
        if (e.getKeyCode() == KeyEvent.VK_KP_DOWN)
            trex.setDown(false);
    }

   // method that increases the score of the player
    public void increaseScoreBy(int points)
    {
        this.score += points;
    }

    // saving the current player's game stats to the ranking list
    public void savePlayerData()
    {
        Player newPLayer = new Player();
        newPLayer.setPlayerName(playerName);
        newPLayer.setScore(score);
        rankingManager.addPLayerToRankingList(newPLayer);
        highScore = rankingManager.getPlayerHS(newPLayer);
    }

    // method that sets up the restart button functionality and aesthetic
    private void showRestartButton()
    {
        restartBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                restartGame();
                characterState = TrexState.RUNNING;
            }
        });

        restartBtn.setBorder(BorderFactory.createEmptyBorder());
        restartBtn.setContentAreaFilled(false);
        restartBtn.setVisible(false);
        add(restartBtn);
    }

    // method that deals with all the drawing that occurs on the Jpanel
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // painting the background
        graphics.setColor(new Color(239, 239, 239));
        graphics.fillRect(0, 0, getWidth(), getHeight());

        // drawing the landscape
        groundObj.drawGround(graphics);
        sky.drawClouds(graphics);
        obstacleManager.drawObstacle(graphics);

        // drawing the trex in its different states
        switch (characterState) {
            case INITIAL_STATE:
            case JUMPING:
                trex.drawJumpingTrex(graphics); // the trex is drawn the same way when jumping and in the initial stage
                break;
            case RUNNING:
                trex.drawRunningTrex(graphics);
                break;
            case DUCKING:
                trex.drawDuckingTrex(graphics);
                break;
            case DEAD:
                trex.drawDeadTrex(graphics);
                // when the trex is dead, it means that the game is over so the "Game Over" text is drawn as well
                graphics.drawImage(gameOverImg, (WINDOW_WIDTH - gameOverImg.getWidth()/2)/2,
                        (WINDOW_HEIGHT - gameOverImg.getHeight()/2)/2 - 70, gameOverImg.getWidth()/2,
                        gameOverImg.getHeight()/2, null);
                break;
        }

        // drawing the informationg about the current game: highscore, score, player name
        graphics.setColor(new Color(65,74,76));
        graphics.setFont(new Font("Monospaced", Font.BOLD, 20));
        graphics.drawString("HI " + String.valueOf(highScore), WINDOW_WIDTH - 150, 30);
        graphics.drawString("SCORE " + String.valueOf(score), WINDOW_WIDTH - 300, 30);
        graphics.drawString("PLAYER: " + playerName, 30, 30);
    }
}