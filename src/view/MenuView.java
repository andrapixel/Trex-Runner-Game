package view;

import utilities.Resources;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MenuView extends JFrame {

    private JPanel menuPanel;
    private JButton startBtn;
    private JButton quitBtn;
    private JButton rulesBtn;
    private JButton rankingButton;
    private JLabel bgdImageLbl;
    private BufferedImage icon;

    public MenuView()
    {
        super("T-Rex Runner Game Menu");
        setContentPane(menuPanel);
        setSize(400, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        icon = Resources.getResourceImg("resources/original/trexIcon.png");
        bgdImageLbl.setIcon(new ImageIcon(icon));

        // adding action listeners for the buttons of the window
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                NewPlayerView newPlayerView = new NewPlayerView();
            }
        });

        rulesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                RulesView rulesView = new RulesView();
            }
        });

        rankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                RankingView newRankingView = new RankingView();
            }
        });

        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
    }
}
