package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import game.GameRecord;
import java.awt.*;
import java.util.List;

public class GameHistoryUI {

    public static void createAndShowGUI(List<GameRecord> gameHistory) {
        JFrame frame = new JFrame("Game History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        frame.setSize(700, 500);

        // Panel with a gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(107, 68, 35); // Brown color from MenuView
                Color color2 = new Color(46, 139, 87); // Green color from MenuView
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Table columns
        String[] columnNames = {"Winner", "Score", "Date", "Duration (mins)"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate the table model
        for (GameRecord record : gameHistory) {
            Object[] row = {
                record.getWinnerName(),
                record.getScore(),
                record.getDate(),
                record.getDuration()
            };
            tableModel.addRow(row);
        }

        // Create and configure the table
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setGridColor(new Color(245, 222, 179)); // Light brown for grid lines
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(46, 139, 87)); // Green header
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(new Color(240, 248, 255));
        table.setForeground(Color.DARK_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add a close button with styling
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(107, 68, 35)); // Brown button
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        closeButton.addActionListener(e -> frame.dispose());

        // Add components to the main panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(closeButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
