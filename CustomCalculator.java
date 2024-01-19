package chatgpt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomCalculator extends JFrame {

    private JTextField textField;
    private StringBuilder calculationSequence;

    public CustomCalculator() {
        // Set up the JFrame
        setTitle("Custom Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize calculation sequence
        calculationSequence = new StringBuilder();

        // Create the text field
        textField = new JTextField();
        textField.setEditable(false);
        add(textField, BorderLayout.NORTH);

        // Create the panel for buttons with a custom layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5)); // Custom layout with 5-pixel gaps

        // Custom button size
        Dimension buttonSize = new Dimension(80, 40);

        // Symbols and numbers for the buttons
        String[] symbols = {"e", "f", "imm", "ce", "p", "f^-1", "ceta", "ec", "no", "tdidf", "pagerank", "->", "<-", ".", "=", "+", "-", "*", "/"};

        // Create and add clickable buttons with custom sizes and symbols
        for (String symbol : symbols) {
            JButton button = new JButton(symbol);
            button.addActionListener(new ButtonClickListener());
            button.setPreferredSize(buttonSize);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Set up the JFrame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ActionListener for the clickable buttons
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            String buttonText = source.getText();

            if (buttonText.equals("=")) {
                // Evaluate and display result when "=" is clicked
                try {
                    String result = Double.toString(CustomExpressionEvaluator.evaluate(calculationSequence.toString()));
                    textField.setText(result);
                    calculationSequence.setLength(0); // Clear the sequence
                    calculationSequence.append(result);
                } catch (IllegalArgumentException e) {
                    textField.setText("Error");
                    calculationSequence.setLength(0);
                }
            } else {
                // Append the button text to the calculation sequence
                calculationSequence.append(buttonText);
                textField.setText(calculationSequence.toString());
            }
        }
    }

    // Main method to run the example
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomCalculator());
    }
}

