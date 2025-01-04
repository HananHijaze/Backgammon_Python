package ui;

import javax.swing.*;
import game.Question;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionDialog extends JDialog {
    private Question question;
    private ButtonGroup answerGroup;
    private String selectedAnswer;

    public QuestionDialog(Frame owner, Question question) {
        super(owner, "Quiz Question", true);
        this.question = question;

        // Set up the dialog layout
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(owner);

        // Gradient panel for the background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(107, 68, 35); // Brown
                Color color2 = new Color(46, 139, 87); // Green
                GradientPaint gradient = new GradientPaint(
                        0, 0, color1,
                        getWidth(), getHeight(), color2
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        add(gradientPanel);

        // Add question text
        JLabel questionLabel = new JLabel("<html><h2>" + question.getQuestion() + "</h2></html>");
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gradientPanel.add(questionLabel, BorderLayout.NORTH);

        // Add answer options as radio buttons
        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        answersPanel.setOpaque(false); // Transparent to show the gradient

        answerGroup = new ButtonGroup();
        for (String answer : question.getAnswers()) {
            JRadioButton answerButton = new JRadioButton(answer);
            answerButton.setFont(new Font("Arial", Font.PLAIN, 16));
            answerButton.setForeground(Color.WHITE);
            answerButton.setOpaque(false); // Transparent to show the gradient
            answerGroup.add(answerButton);
            answersPanel.add(answerButton);

            // Set selected answer when clicked
            answerButton.addActionListener(e -> selectedAnswer = answer);
        }

        gradientPanel.add(answersPanel, BorderLayout.CENTER);

        // Add submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(107, 68, 35)); // Brown button
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate selected answer
                if (selectedAnswer == null) {
                    JOptionPane.showMessageDialog(
                            QuestionDialog.this,
                            "Please select an answer.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Close the dialog
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent to show the gradient
        buttonPanel.add(submitButton);
        gradientPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }
}
