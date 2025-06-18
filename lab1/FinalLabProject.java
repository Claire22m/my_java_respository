import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorApp extends JFrame {
    private final JTextField display;

    public CalculatorApp() {
        setTitle("Java Swing Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        // Font for buttons and display
        Font displayFont = new Font("Arial", Font.BOLD, 32);
        Font buttonFont = new Font("Arial", Font.PLAIN, 24);

        // Display panel
        display = new JTextField();
        display.setFont(displayFont);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Buttons panel with grid layout
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(6, 4, 12, 12)); // 6 rows, 4 columns, gaps

        // Button texts with order:
        // Row 1: Clear, Delete, %, /
        // Row 2: 7, 8, 9, *
        // Row 3: 4, 5, 6, -
        // Row 4: 1, 2, 3, +
        // Row 5: +/-, 0, ., =
        // Row 6: sqrt, ^, (empty), (empty)

        // But per instructions, decimal point button is required? It wasn't specified, but inputting decimal numbers is standard; specs don't mention decimal support. 
        // To handle typical calculations, I will include '.' decimal point button.

        String[] buttonLabels = {
            "C", "DEL", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "±", "0", ".", "=",
            "√", "^", "", ""
        };

        for (String label : buttonLabels) {
            if (label.isEmpty()) {
                buttonsPanel.add(new JLabel()); // empty placeholder
            } else {
                JButton button = new JButton(label);
                button.setFont(buttonFont);
                button.setFocusPainted(false);
                button.setBackground(new Color(245, 245, 245));
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createRoundedBorder(8));
                button.addActionListener(e -> onButtonClick(label));
                buttonsPanel.add(button);
            }
        }

        JPanel mainPanel = new JPanel(new BorderLayout(16, 16));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(display, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void onButtonClick(String label) {
        switch (label) {
            case "C":
                display.setText("");
                break;
            case "DEL":
                String currentText = display.getText();
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
                break;
            case "=":
                calculateResult();
                break;
            case "±":
                togglePlusMinus();
                break;
            case "√":
                applySquareRoot();
                break;
            default:
                // Append label for numbers, operators, decimal point
                appendToDisplay(label);
                break;
        }
    }

    private void appendToDisplay(String text) {
        // Prevent multiple '.' in a number segment is not mandatory per spec, but useful
        if (text.equals(".")) {
            // prevent multiple dots in current number segment
            String current = display.getText();
            int lastOperatorIndex = Math.max(
                Math.max(current.lastIndexOf("+"), current.lastIndexOf("-")),
                Math.max(current.lastIndexOf("*"), Math.max(current.lastIndexOf("/"), current.lastIndexOf("%")))
            );
            String lastNumberSegment = current.substring(lastOperatorIndex + 1);
            if (lastNumberSegment.contains(".")) {
                return; // ignore additional dot
            }
            if (lastNumberSegment.isEmpty()) {
                // If dot pressed right after operator without digits, prepend 0
                display.setText(current + "0.");
                return;
            }
        }
        display.setText(display.getText() + text);
    }

    private void togglePlusMinus() {
        String text = display.getText();
        if (text.isEmpty()) {
            display.setText("-");
            return;
        }
        // Try to find last number and toggle its sign
        // A heuristic approach: find last number or expression after last operator
        // We'll try to wrap the last number with (-number) or remove it.

        // For simplicity and reliability: 
        // If entire display starts with '-', remove it; else prepend '-'
        // Only works for the entire expression, but for small scope it's acceptable.

        if (text.startsWith("-")) {
            display.setText(text.substring(1));
        } else {
            display.setText("-" + text);
        }
    }

    private void applySquareRoot() {
        String text = display.getText();
        if (text.isEmpty()) {
            return;
        }
        try {
            double val = evaluateExpression(text);
            if (val < 0) {
                display.setText("Error: Negative √");
                return;
            }
            double sqrtResult = Math.sqrt(val);
            // Format output to avoid trailing zeros
            display.setText(formatDouble(sqrtResult));
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void calculateResult() {
        String expression = display.getText();
        if (expression.isEmpty()) return;
        try {
            double result = evaluateExpression(expression);
            display.setText(formatDouble(result));
        } catch (ArithmeticException ae) {
            display.setText("Error: " + ae.getMessage());
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private String formatDouble(double val) {
        if (val == (long) val) {
            return String.format("%d", (long) val);
        } else {
            return String.format("%s", val);
        }
    }

    /**
     * Evaluate a mathematical expression supporting:
     * +, -, *, /, %, ^ operators, support negative numbers and decimals
     * This implementation uses Shunting Yard algorithm to parse expression
     * and evalute with proper operator precedence and associativity.
     */
    private double evaluateExpression(String expression) throws Exception {
        // Remove spaces
        expression = expression.replaceAll("\\s+", "");
        if (expression.isEmpty()) throw new Exception("Empty expression");

        // Tokenize
        String[] tokens = tokenize(expression);

        // Convert to Reverse Polish Notation (Postfix) via Shunting Yard
        String[] postfix = toPostfix(tokens);

        // Evaluate postfix
        return evalPostfix(postfix);
    }

    private String[] tokenize(String expr) throws Exception {
        // Pattern for number (integer or decimal), operators and parentheses
        // We also allow negative numbers via leading '-'
        Pattern pattern = Pattern.compile(
                "(\\d*\\.\\d+|\\d+)|[+\\-*/%^()]"
        );
        Matcher matcher = pattern.matcher(expr);
        java.util.List<String> tokens = new java.util.ArrayList<>();
        int pos = 0;
        while (pos < expr.length()) {
            if (matcher.find(pos) && matcher.start() == pos) {
                String token = matcher.group();
                tokens.add(token);
                pos = matcher.end();
            } else {
                throw new Exception("Invalid character at position " + pos);
            }
        }
        // To handle unary minus, we will preprocess tokens:
        // Replace unary minus with "0" and "-" for easier parsing
        // e.g. "-3+5" tokens as ["-", "3", "+", "5"]
        // Convert leading '-' or after '(' or operator to unary minus logic

        java.util.List<String> processedTokens = new java.util.ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals("-")) {
                if (i == 0 || 
                    tokens.get(i - 1).equals("(") || 
                    isOperator(tokens.get(i - 1))) {
                    // Unary minus detected
                    // Insert '0' before '-'
                    processedTokens.add("0");
                    processedTokens.add("-");
                } else {
                    processedTokens.add(token);
                }
            } else {
                processedTokens.add(token);
            }
        }
        return processedTokens.toArray(new String[0]);
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") ||
               token.equals("/") || token.equals("%") || token.equals("^");
    }

    private int getPrecedence(String op) {
        switch (op) {
            case "+":
            case "-": return 1;
            case "*":
            case "/":
            case "%": return 2;
            case "^": return 3; // exponent has higher priority
            default: return 0;
        }
    }

    private boolean isRightAssociative(String op) {
        return op.equals("^"); // exponent is right associative
    }

    private String[] toPostfix(String[] tokens) throws Exception {
        Stack<String> stack = new Stack<>();
        java.util.List<String> output = new java.util.ArrayList<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek())) {
                    String topOp = stack.peek();
                    int topPrecedence = getPrecedence(topOp);
                    int tokenPrecedence = getPrecedence(token);

                    if ((isRightAssociative(token) && tokenPrecedence < topPrecedence) ||
                        (!isRightAssociative(token) && tokenPrecedence <= topPrecedence)) {
                        output.add(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                boolean leftParenFound = false;
                while (!stack.isEmpty()) {
                    String top = stack.pop();
                    if (top.equals("(")) {
                        leftParenFound = true;
                        break;
                    } else {
                        output.add(top);
                    }
                }
                if (!leftParenFound) {
                    throw new Exception("Mismatched parentheses");
                }
            } else {
                throw new Exception("Unknown token: " + token);
            }
        }

        while (!stack.isEmpty()) {
            String top = stack.pop();
            if (top.equals("(") || top.equals(")")) {
                throw new Exception("Mismatched parentheses");
            }
            output.add(top);
        }

        return output.toArray(new String[0]);
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double evalPostfix(String[] postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) throw new Exception("Invalid syntax");
                double b = stack.pop();
                double a = stack.pop();
                double res;
                switch (token) {
                    case "+": res = a + b; break;
                    case "-": res = a - b; break;
                    case "*": res = a * b; break;
                    case "/": 
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        res = a / b; break;
                    case "%": 
                        if (b == 0) throw new ArithmeticException("Modulo by zero");
                        res = a % b; break;
                    case "^": res = Math.pow(a, b); break;
                    default: throw new Exception("Unknown operator " + token);
                }
                stack.push(res);
            } else {
                throw new Exception("Invalid token in postfix: " + token);
            }
        }
        if (stack.size() != 1) throw new Exception("Invalid expression");
        return stack.pop();
    }

    public static void main(String[] args) {
        // Ensure we use system look and feel for modern appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Create and show calculator
        SwingUtilities.invokeLater(() -> {
            CalculatorApp calculator = new CalculatorApp();
            calculator.setVisible(true);
        });
    }
}

          
