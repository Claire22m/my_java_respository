import java.awt.*;
import java.awt.event.*;

public class QuizApp extends Frame implements ActionListener {
    // Questions, options, and answers
    String[] questions = {
        "Which language is used for Android development?",
        "What is the result of 2 + 2 * 2?",
        "Which data structure uses LIFO principle?"
    };

    String[][] options = {
        {"Swift", "Java", "Python", "Kotlin"},
        {"6", "8", "4", "10"},
        {"Queue", "Stack", "Array", "Linked List"}
    };

    int[] answers = {1, 1, 1}; // Indexes of correct options

    // UI Components
    Label questionLabel;
    CheckboxGroup choicesGroup;
    Checkbox[] choiceButtons = new Checkbox[4];
    Button nextButton;
    Panel choicesPanel;

    // State variables
    int currentQuestion = 0;
    int score = 0;

    public QuizApp() {
        // Setup frame
        setSize(500, 300);
        setLayout(new BorderLayout());
        setTitle("Quiz App");

        // Question Label
        questionLabel = new Label("", Label.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        // Choices Panel
        choicesPanel = new Panel();
        choicesPanel.setLayout(new GridLayout(4, 1));

        choicesGroup = new CheckboxGroup();
        for (int i = 0; i < 4; i++) {
            choiceButtons[i] = new Checkbox("", choicesGroup);
            styleCheckbox(choiceButtons[i]);
            choicesPanel.add(choiceButtons[i]);
        }
        add(choicesPanel, BorderLayout.CENTER);

        // Next Button
        nextButton = new Button("Next");
        styleButton(nextButton);
        nextButton.addActionListener(this);
        Panel bottomPanel = new Panel();
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Window close operation
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // Load first question
        loadQuestion();

        setVisible(true);
    }

    // Apply styles to checkboxes
    private void styleCheckbox(Checkbox cb) {
        cb.setFont(new Font("Arial", Font.PLAIN, 14));
        // Additional customization can be added here
    }

    // Apply styles to button
    private void styleButton(Button btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        // Additional customization can be added here
    }

    // Load question and options
    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText(questions[currentQuestion]);
            for (int i = 0; i < 4; i++) {
                choiceButtons[i].setLabel(options[currentQuestion][i]);
                choiceButtons[i].setState(false);
            }
        } else {
            showFinalScore();
        }
    }

    // Show final score
    private void showFinalScore() {
        questionLabel.setText("Quiz Completed! Your Score: " + score + " out of " + questions.length);
        for (Checkbox cb : choiceButtons) {
            cb.setEnabled(false);
        }
        nextButton.setLabel("Finish");
        nextButton.removeActionListener(this);
        nextButton.addActionListener(e -> System.exit(0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check selected answer
        int selectedIndex = -1;
        for (int i = 0; i < 4; i++) {
            if (choiceButtons[i].getState()) {
                selectedIndex = i;
                break;
            }
        }

        if (selectedIndex == -1) {
            // No selection made
            return;
        }

        // Update score if correct
        if (selectedIndex == answers[currentQuestion]) {
            score++;
        }

        currentQuestion++;
        loadQuestion();
    }

    public static void main(String[] args) {
        new QuizApp();
    }
    }
