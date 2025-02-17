/*

    Kaan GÜNER
    ID: 220201068

 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgrammerCalculator extends JFrame{
    // Display field for input and results
    private final JTextField display;
    // Radio buttons to select number mode: Hexadecimal, Decimal, or Binary
    private final JRadioButton hexMode;
    private final JRadioButton decMode;
    private final JRadioButton binMode;
    // Array of buttons for numbers and operations
    private final JButton[] buttons;

    public ProgrammerCalculator() {
        // Set up the main frame
        JFrame frame = new JFrame("Programmer Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);

        // Create the display field
        display = new JTextField();
        display.setBounds(20, 20, 440, 50);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        frame.add(display);

        // Create a button group for mode selection (Hex, Dec, Bin)
        ButtonGroup modeGroup = new ButtonGroup();

        // Hexadecimal mode radio button
        hexMode = new JRadioButton("Hexadecimal");
        hexMode.setBounds(20, 80, 120, 30);
        frame.add(hexMode);
        modeGroup.add(hexMode);

        // Decimal mode radio button (default selection)
        decMode = new JRadioButton("Decimal");
        decMode.setBounds(150, 80, 100, 30);
        frame.add(decMode);
        decMode.setSelected(true);
        modeGroup.add(decMode);

        // Binary mode radio button
        binMode = new JRadioButton("Binary");
        binMode.setBounds(270, 80, 100, 30);
        frame.add(binMode);
        modeGroup.add(binMode);

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(20, 120, 440, 300);
        buttonPanel.setLayout(new GridLayout(6, 5, 10, 10));
        frame.add(buttonPanel);

        // Labels for all calculator buttons
        String[] buttonLabels = {
                "A", "B", "C", " << ", " Clear ",
                "D", "E", "F", " >> ", " NOT ",
                "7", "8", "9", " + ", " - ",
                "4", "5", "6", " * ", " / ",
                "1", "2", "3", " And ", " Or ",
                "", "0", "", " XOR ", " = "
        };

        // Create buttons and add them to the button panel
        buttons = new JButton[30];
        for (int i = 0; i < buttonLabels.length; i++) {
            String label = buttonLabels[i];
            buttons[i] = new JButton(label);
            buttons[i].setFont(new Font("Arial",Font.BOLD, 16));

            // Disable buttons with empty labels
            if (label.isEmpty()) {
                buttons[i].setEnabled(false);
            } else {
                buttons[i].addActionListener(new ButtonClickListener()); // Add action listeners
            }

            buttonPanel.add(buttons[i]); // Add button to the panel
        }

        // Update button states based on the selected mode
        updateButtonStates();

        // Add listeners to radio buttons to switch modes
        hexMode.addActionListener(e -> updateButtonStates());
        decMode.addActionListener(e -> updateButtonStates());
        binMode.addActionListener(e -> updateButtonStates());

        // Make the frame visible
        frame.setVisible(true);
    }

    // Updates button states based on the selected mode (Hex, Dec, Bin)
    private void updateButtonStates() {
        for (JButton button : buttons) {
            if (button == null) continue; // Skip null buttons
            String label = button.getText(); // Get the button's label

            if ("ABCDEF".contains(label)) {
                // Enable A-F buttons only in Hexadecimal mode
                button.setEnabled(hexMode.isSelected());
            } else if ("23456789".contains(label)) {
                // Enable 2-9 buttons only in Decimal mode
                button.setEnabled(!binMode.isSelected());
            } else {
                // Enable all other buttons (operators and 0/1) in all modes
                button.setEnabled(true);
            }
        }

        // Clear the display when mode changes
        display.setText("");
    }

    // Action listener for button clicks
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();
            if (command.equals(" Clear ")) {
                display.setText(""); // Clear the display
            } else if (command.equals(" = ")) {
                String expression = display.getText(); // Get the expression from the display
                String result = calculate(expression); // Perform the calculation
                display.setText(result); // Show the result
            } else {
                display.setText(display.getText() + command); // Append the button text to the display
            }
        }
    }

    // Calculate the result of the given expression
    private String calculate(String expression) {
        String[] elements = expression.split(" "); // Split the expression into parts
        if (elements.length < 3) throw new IllegalArgumentException("Invalid expression"); // Validate input

        String operator = elements[1]; // Extract the operator

        int num1 = 0, num2 = 0, result;

        // Parse the numbers based on the selected mode
        if (hexMode.isSelected()) {
            num1 = Integer.parseInt(elements[0], 16);
            num2 = Integer.parseInt(elements[2], 16);
        } else if (decMode.isSelected()) {
            num1 = Integer.parseInt(elements[0]);
            num2 = Integer.parseInt(elements[2]);
        } else if (binMode.isSelected()) {
            num1 = Integer.parseInt(elements[0], 2);
            num2 = Integer.parseInt(elements[2], 2);
        }

        // Perform the operation based on the operator
        switch (operator) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "/" -> result = num1 / num2;
            case "And" -> result = num1 & num2;
            case "Or" -> result = num1 | num2;
            case "Xor" -> result = num1 ^ num2;
            case "<<" -> result = num1 << num2;
            case ">>" -> result = num1 >> num2;
            default -> throw new IllegalArgumentException("Invalid Operator");
        }

        // Convert the result to the appropriate format based on the mode
        if (hexMode.isSelected()) {
            return Integer.toHexString(result).toUpperCase();
        } else if (decMode.isSelected()) {
            return Integer.toString(result);
        } else {
            return Integer.toBinaryString(result);
        }
    }

    // Main method to run the calculator
    public static void main(String[] args) {
        new ProgrammerCalculator();
    }
}
