package memoGUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener {
	private JTextField textField;
    private JButton[] numberButtons;
    private JButton[] operatorButtons;
    private JButton addButton, subtractButton, multiplyButton, divideButton;
    private JButton decimalButton, equalsButton, clearButton;
    private JPanel panel;

    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public Calculator() {
        setTitle("계산기");
        setSize(300, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textField = new JTextField();
        textField.setBounds(30, 30, 240, 30);
        textField.setEditable(false);

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
        }

        operatorButtons = new JButton[4];
        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");

        operatorButtons[0] = addButton;
        operatorButtons[1] = subtractButton;
        operatorButtons[2] = multiplyButton;
        operatorButtons[3] = divideButton;

        decimalButton = new JButton(".");
        equalsButton = new JButton("=");
        clearButton = new JButton("C");

        panel = new JPanel();
        panel.setBounds(30, 80, 240, 240);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subtractButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(multiplyButton);
        panel.add(decimalButton);
        panel.add(numberButtons[0]);
        panel.add(equalsButton);
        panel.add(divideButton);

        clearButton.setBounds(30, 330, 240, 30);

        add(textField);
        add(panel);
        add(clearButton);

        for (int i = 0; i < 10; i++) {
            numberButtons[i].addActionListener(this);
        }

        for (int i = 0; i < 4; i++) {
            operatorButtons[i].addActionListener(this);
        }

        decimalButton.addActionListener(this);
        equalsButton.addActionListener(this);
        clearButton.addActionListener(this);

        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Calculator();
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                textField.setText(textField.getText() + i);
            }
        }

        if (e.getSource() == decimalButton) {
            textField.setText(textField.getText() + ".");
        }

        if (e.getSource() == addButton) {
            num1 = Double.parseDouble(textField.getText());
            operator = '+';
            textField.setText("");
        }

        if (e.getSource() == subtractButton) {
            num1 = Double.parseDouble(textField.getText());
            operator = '-';
            textField.setText("");
        }

        if (e.getSource() == multiplyButton) {
            num1 = Double.parseDouble(textField.getText());
            operator = '*';
            textField.setText("");
        }

        if (e.getSource() == divideButton) {
            num1 = Double.parseDouble(textField.getText());
            operator = '/';
            textField.setText("");
        }

        if (e.getSource() == equalsButton) {
            num2 = Double.parseDouble(textField.getText());

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num1 / num2;
                    break;
            }

            textField.setText(String.valueOf(result));
        }

        if (e.getSource() == clearButton) {
            textField.setText("");
        }
    }
}