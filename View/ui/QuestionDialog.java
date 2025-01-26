package ui;

import javax.swing.*;
import game.Question;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class QuestionDialog extends JDialog {
    private Question question;
    private ButtonGroup answerGroup;
    private String selectedAnswer;

    public QuestionDialog(Question question, Consumer<String> callback) {
        super((Frame) null, "Quiz Question", false); // Non-modal dialog
        this.question = question;

        // Dialog setup
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Gradient background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(107, 68, 35), // Brown
                        getWidth(), getHeight(), new Color(46, 139, 87) // Green
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        add(gradientPanel);

        // Question text
        JLabel questionLabel = new JLabel("<html><h2>" + question.getQuestion() + "</h2></html>");
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gradientPanel.add(questionLabel, BorderLayout.NORTH);

        // Answer options
        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        answersPanel.setOpaque(false);

        answerGroup = new ButtonGroup();
        for (String answer : question.getAnswers()) {
            JRadioButton answerButton = new JRadioButton(answer);
            answerButton.setFont(new Font("Arial", Font.PLAIN, 16));
            answerButton.setForeground(Color.WHITE);
            answerButton.setOpaque(false);
            answerGroup.add(answerButton);
            answersPanel.add(answerButton);
            answerButton.addActionListener(e -> selectedAnswer = answer);
        }
        gradientPanel.add(answersPanel, BorderLayout.CENTER);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(107, 68, 35));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        submitButton.addActionListener((ActionEvent e) -> {
            if (selectedAnswer == null) {
                JOptionPane.showMessageDialog(this, "Please select an answer.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                callback.accept(selectedAnswer);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(submitButton);
        gradientPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
