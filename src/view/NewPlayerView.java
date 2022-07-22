package view;

import utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class NewPlayerView extends JFrame {

    private JPanel secondaryPanel;
    private JButton nextBtn;
    private JTextField playerNameTxt;
    private JLabel textLengthWarningLbl;
    private JLabel iconLbl;
    private JButton backBtn;
    private BufferedImage icon;

    public NewPlayerView()
    {
        super("T-Rex Runner Game");
        setSize(500, 380);
        setContentPane(secondaryPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // adding an icon for aesthetic-purposes :)
        icon = Resources.getResourceImg("resources/original/trexIcon.png");
        iconLbl.setIcon(new ImageIcon(icon));

        // part of code that deals with the warning about exceeding the character limit of the name text field
        textLengthWarningLbl.setForeground(Color.RED);
        textLengthWarningLbl.setText("Text exceeded character limit.");
        textLengthWarningLbl.setVisible(false);

        // Action listeners for the name text field, buttons and window
        playerNameTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);

                // making the warning label visible depending on whether the input text has more or less than 20 characters
                if (playerNameTxt.getText().length() >= 20)
                {
                    e.consume();
                    textLengthWarningLbl.setVisible(true);
                }

                if (playerNameTxt.getText().length() < 20)
                    textLengthWarningLbl.setVisible(false);
            }
        });


        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // if no name was entered, an error message pops up
                if (playerNameTxt.getText().isEmpty())
                {
                    String errorMsg = "\nPlease enter your name!\n";
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), errorMsg, "Invalid Name", JOptionPane.ERROR_MESSAGE);
                }

                // otherwise the game window will open
                if (!playerNameTxt.getText().isEmpty())
                {
                    dispose();
                    GameView gameWindow = new GameView(playerNameTxt.getText());
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                MenuView menuView = new MenuView();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                MenuView menuView = new MenuView();
            }
        });
    }
}