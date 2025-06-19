
package LabActivity5;

import java.awt.*;
import java.awt.event.*;

public class LabActivity5QuizAppAWT extends Frame {

    // Array for questions   
    String[] questions = {"Mic is example of which type of device, Output, or Input?", 
                          "What is the full Form of RAM?",
                          "Who is known as the Father of Computers?"
                      };
    // Array for options
    String[][] options = {
                          {"A.Input", "B.Output", "C.Storage", "D.None Of The above"},
                          {"A.Random Access Memory", "B.Read Only Memory", "C.Random All Memory", "D.None Of The above"},
                          {"A.Blaise Pascal", "B.Charles Babbage", "C.Alan Turing", "D.None Of The above"}
                      };
    //Array for Answerkey
    String[] answerKey = {"A.Input", "A.Random Access Memory", "B.Charles Babbage"};

    // Labels and Checkbox   
    Label lQuestion;
    Label warnLabel;
    Checkbox rOptionOne, rOptionTwo, rOptionThree, rOptionFour;
    CheckboxGroup optionGroup;

    // Panels
    Panel FPanel, SPanel, TPanel,FTPanel;

    // Button     
    Button next;
     
    // Counters
    int count = 0;
    int score = 0;

    // Font
    Font font;
    Font SFont;

    public LabActivity5QuizAppAWT() {
        setTitle("Quiz App AWT");
        setVisible(true);
        setSize(500, 300);
        setLayout(new GridLayout(3, 1));
        font  = new Font("Arial", Font.BOLD , 16);
        SFont = new Font("Arial", Font.PLAIN, 12);
        

        // First panel for question
        FPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        lQuestion = new Label("", Label.CENTER);
        lQuestion.setFont(font);
        FPanel.add(lQuestion);
        add(FPanel);

        // Second panel for radio buttons
        SPanel = new Panel(new GridLayout(2, 2));
        optionGroup  = new CheckboxGroup();
        rOptionOne   = new Checkbox("", optionGroup, false);
        rOptionOne.setForeground  (Color.MAGENTA);
        SPanel.add(rOptionOne);
        rOptionTwo   = new Checkbox("", optionGroup, false);
        rOptionTwo.setForeground  (Color.MAGENTA);
        SPanel.add(rOptionTwo);
        rOptionThree = new Checkbox("", optionGroup, false);
        rOptionThree.setForeground(Color.MAGENTA);
        SPanel.add(rOptionThree);
        rOptionFour  = new Checkbox("", optionGroup, false);
        rOptionFour.setForeground(Color.MAGENTA);
        SPanel.add(rOptionFour);
        add(SPanel);

        // Third Panel for warning label and next button
        FTPanel = new Panel(new GridLayout(2,1));
        TPanel = new Panel(new BorderLayout());
        warnLabel = new Label("Please select an answer.", Label.CENTER);
        warnLabel.setFont(SFont);
        warnLabel.setVisible(false); 
        next = new Button("Next");
        next.setFont(font);
        FTPanel.add(warnLabel);
        FTPanel.add(next);
        TPanel.add(FTPanel, BorderLayout.SOUTH);
        add(TPanel);
        
        // Show first question
        loadQuestion(count);

        // Action listener for next button
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Checkbox selected = optionGroup.getSelectedCheckbox();
                    if (selected == null) {
                        warnLabel.setVisible(true);
                    } else {
                       warnLabel.setVisible(false); 
                       checkAnswer();
                          count++;
                    if (count < questions.length) {
                        loadQuestion(count);
                    } else {
                        showResult();
                    }
                }
            }
        });
        // Closing window
        addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    dispose(); 
                }
            });   
    }

    // Loading Questions
    public void loadQuestion(int index) {
        lQuestion.setText    (questions[index]);
        rOptionOne.setLabel  (options[index][0]);
        rOptionTwo.setLabel  (options[index][1]);
        rOptionThree.setLabel(options[index][2]);
        rOptionFour.setLabel (options[index][3]);
        optionGroup.setSelectedCheckbox(null); 
    }

    // Checking selected answer
    private void checkAnswer() {
        Checkbox selected = optionGroup.getSelectedCheckbox();
            if (selected != null && selected.getLabel().equals(answerKey[count])) {
                score++;
        }
    }
    // Showing score 
    private void showResult() {
        lQuestion.setText("Quiz Completed! Your Score: " + score + " out of " + questions.length); 
        rOptionOne.setEnabled   (false);
        rOptionTwo.setEnabled   (false);
        rOptionThree.setEnabled (false);
        rOptionFour.setEnabled  (false);
        next.setEnabled         (false);
    }

    public static void main(String[] args) {
        new LabActivity5QuizAppAWT();
    }
}
