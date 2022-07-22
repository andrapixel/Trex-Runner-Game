package view;

import game.objects.Player;
import game.managers.RankingManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class RankingView extends JFrame {

    private JPanel rankingPanel;
    private JTable rankingTable;
    private JScrollPane rankingScrollPane;
    private JButton backBtn;

    private RankingManager rankingManager = RankingManager.getInstance();

    public RankingView()
    {
        super("T-Rex Runner Game");
        setSize(400, 500);
        setContentPane(rankingPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        DefaultTableModel tableModel = new DefaultTableModel();
        configureRankingTableDate(tableModel);

        // Back button and Window action listeners
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

    // method that sets up the contents of the table
    private void configureRankingTableDate(DefaultTableModel model)
    {
        String[] columnNames = {"Game", "Player Name", "Score", "HighScore"};

        // adding the column headers to the table
        for(String columnName : columnNames){
            model.addColumn(columnName);
        }

        addRowToRankingTable(model);
        rankingTable.setModel(model);
    }

    // method that adds data about the players into the table
    private void addRowToRankingTable(DefaultTableModel model)
    {
        ArrayList<Player> players = rankingManager.getPlayerRanking();

        for (int i = 0; i < players.size(); ++i)
        {
            model.addRow(new Object[]{String.valueOf(i + 1), players.get(i).getPlayerName(), String.valueOf(players.get(i).getScore()),
                    rankingManager.getPlayerHS(players.get(i))});
        }
    }
}
