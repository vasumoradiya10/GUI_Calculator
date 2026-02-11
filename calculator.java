import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    private final JTextField display;
    private double firstOperand = 0;
    private String operator = "";
    private boolean isNewCalculation = true;

    public Calculator() {
        setTitle("Swing Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new BorderLayout(10, 10));

        // --- Display Screen ---
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 40));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setText("0");
        add(display, BorderLayout.NORTH);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        
        // Button labels
        String[] buttons = {
            "C", "±", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".","DEL", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            // Color coding for visual distinction
            if ("C/=*+-".contains(text)) {
                 button.setBackground(new Color(255, 190, 120)); // Orange for operators
            }
            if ("=".equals(text)){
                button.setBackground(new Color(150, 255, 150)); // Green for equals
            }
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        // Handle number and decimal inputs
        if ("0123456789.".contains(command)) {
            if (isNewCalculation) {
                display.setText(command);
                isNewCalculation = false;
            } else {
                display.setText(display.getText() + command);
            }
        } 
        // Handle Clear (C) button
        else if ("C".equals(command)) {
            display.setText("0");
            firstOperand = 0;
            operator = "";
            isNewCalculation = true;
        } 
        // Handle Equals (=) button
        else if ("=".equals(command)) {
            if (!operator.isEmpty()) {
                double secondOperand = Double.parseDouble(display.getText());
                double result = calculate(firstOperand, secondOperand, operator);
                display.setText(String.valueOf(result));
                operator = "";
                isNewCalculation = true;
            }
        } 
        // Handle operator buttons
        else {
            if (!operator.isEmpty() && !isNewCalculation) {
                // If an operator is already present, calculate the intermediate result first
                double secondOperand = Double.parseDouble(display.getText());
                firstOperand = calculate(firstOperand, secondOperand, operator);
                display.setText(String.valueOf(firstOperand));
            } else {
                firstOperand = Double.parseDouble(display.getText());
            }
            operator = command;
            isNewCalculation = true;
        }
    }

    private double calculate(double num1, double num2, String op) {
        switch (op) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
                return num1 / num2;
            default:
                return num2;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Calculator().setVisible(true);
        });
    }
}
