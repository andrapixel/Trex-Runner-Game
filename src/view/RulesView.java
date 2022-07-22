package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RulesView extends JFrame {

    private JPanel rulesPanel;
    private JTextArea rulesTextArea;
    private JButton backBtn;

    public RulesView()
    {
        super("T-Rex Runner Game");
        setContentPane(rulesPanel);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Action listener for the Back button
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                MenuView menuView = new MenuView();
            }
        });
    }
}
