package com.quiz.management;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Objects;

public final class StartQuiz extends javax.swing.JFrame {
    
    private int currentQuestionIndex = 0;
    private int currentAnswerIndex = -1; // Default to no selection
    private int totalScore = 0;
    private List<List<String>> questions;
    private DatabaseConnection databaseConnection;
    private List<Integer> selectedAnswers;
    
    public StartQuiz() {
        initComponents();
        loadQuestions();
    }
    
    private void loadQuestions() {
        try {
            databaseConnection = new DatabaseConnection();
            databaseConnection.connect();
            questions = databaseConnection.getQuestions();
            selectedAnswers = new ArrayList<>(Collections.nCopies(questions.size(), -1)); // Initialize with -1 for all questions
            loadQuestion(currentQuestionIndex); // Load the first question
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading questions: " + e.getMessage());
        }
    }
    
    public void loadQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            List<String> questionData = questions.get(index);
            jLabel1.setText("Q" + (index + 1) + "] " + questionData.get(0));
            jRadioButton1.setText(questionData.get(1));
            jRadioButton2.setText(questionData.get(2));
            jRadioButton3.setText(questionData.get(3));
            jRadioButton4.setText(questionData.get(4));
            Integer.valueOf(questionData.get(5));

            currentAnswerIndex = selectedAnswers.get(index); // Get the saved answer
            jRadioButton1.setSelected(currentAnswerIndex == 1);
            jRadioButton2.setSelected(currentAnswerIndex == 2);
            jRadioButton3.setSelected(currentAnswerIndex == 3);
            jRadioButton4.setSelected(currentAnswerIndex == 4);
        } else {
            JOptionPane.showMessageDialog(null, "No more questions available.", "Quiz Finished", JOptionPane.INFORMATION_MESSAGE);
        }
        
        updateNavigationButtons();
    }
     
    public void addCurrentState() {
        if (currentAnswerIndex != -1) {  // Ensure an answer is selected
            selectedAnswers.set(currentQuestionIndex, currentAnswerIndex); // Save selected answer
        }
    }
    
    private void calculateFinalScore() {
        totalScore = 0; // Reset score
        for (int i = 0; i < selectedAnswers.size(); i++) {
            if (Objects.equals(selectedAnswers.get(i), Integer.valueOf(questions.get(i).get(5)))) {
                totalScore++;
            }
        }
    }
    
    // Event handler for radio buttons (shared between options)
    private void handleRadioButtonAction(int selectedOption) {
        currentAnswerIndex = selectedOption;
        jRadioButton1.setSelected(selectedOption == 1);
        jRadioButton2.setSelected(selectedOption == 2);
        jRadioButton3.setSelected(selectedOption == 3);
        jRadioButton4.setSelected(selectedOption == 4);
    }
    
    private void updateNavigationButtons() {
        jButton2.setEnabled(currentQuestionIndex > 0);
        jButton3.setEnabled(currentQuestionIndex < questions.size() - 1);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setText("BACK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Q] Will Question Appear Here? If Yes Justify.");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton1.setText("Option1");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Option2");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jRadioButton3.setText("Option3");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jRadioButton4.setText("Option4");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 204, 255));
        jButton2.setText("Previous");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setText("Next");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(153, 255, 153));
        jButton4.setText("Submit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1)
                    .addComponent(jLabel1))
                .addContainerGap(268, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Home home = new Home();
        home.setTitle("Home");
        home.setSize(700, 500);
        home.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        handleRadioButtonAction(2);
        addCurrentState();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        handleRadioButtonAction(4);
        addCurrentState();
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        handleRadioButtonAction(1);
        addCurrentState();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        handleRadioButtonAction(3);
        addCurrentState();
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (currentQuestionIndex + 1 < questions.size()) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        calculateFinalScore(); // Calculate the final score
        SavedData savedData = new SavedData();
        try {
            databaseConnection.addResult(savedData.getPreference("username"), totalScore);
            databaseConnection.updateLeaderboard();
            databaseConnection.addHistory(savedData.getPreference("username"));
            databaseConnection.showDialog("Your score: " + totalScore);
            databaseConnection.disconnect(); // Close the connection
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error disconnecting database: " + ex.getMessage());
        }
        this.setVisible(false);
        Home home = new Home();
        home.setTitle("Home");
        home.setSize(700, 500);
        home.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new StartQuiz().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    // End of variables declaration//GEN-END:variables
}
