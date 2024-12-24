package com.quiz.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Arrays;

public class DatabaseConnection {
    
    private Connection connection;
    private final String databaseURL;
    private final String db_username;
    private final String db_password;
    
    public DatabaseConnection() {
        this.databaseURL = "jdbc:mysql://localhost:3306/quiz_manager";
        this.db_username = "root";
        this.db_password = "swapspace";
    }
    
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(databaseURL, db_username, db_password);
    }
    
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    public Map<String, String> getUsers() throws SQLException {
        String query = "SELECT * FROM users";
        Map<String, String> userCredentials = new HashMap<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                userCredentials.put(username, password);  // Store as key-value pair
            }
        } catch (SQLException e) {
            showDialog("Error fetching users: " + e.getMessage());
        }

        return userCredentials;
    }

    public void addUser(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the values for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the update
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Proper exception handling
            showDialog("Error adding user: " + e.getMessage());
        }
    }
    
    public void addQuestions(String question, String a, String b, String c, String d, String answer) throws SQLException {
        String query = "INSERT INTO questions (question, a, b, c, d, answer) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the values for the prepared statement
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, a);
            preparedStatement.setString(3, b);
            preparedStatement.setString(4, c);
            preparedStatement.setString(5, d);
            preparedStatement.setString(6, answer);

            // Execute the update
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Proper exception handling
            showDialog("Error adding question: " + e.getMessage());
        }
    }

    public List<List<String>> getQuestions() throws SQLException {
        String query = "SELECT * FROM questions";
        List<List<String>> questions = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String a = resultSet.getString("a");
                String b = resultSet.getString("b");
                String c = resultSet.getString("c");
                String d = resultSet.getString("d");
                String answer = resultSet.getString("answer");

                // Create a list with options and the answer
                List<String> questionDetails = Arrays.asList(question, a, b, c, d, answer);

                questions.add(questionDetails);
            }
        } catch (SQLException e) {
            showDialog("Error fetching questions: " + e.getMessage());
        }

        return questions;
    }

    
    public void addResult(String username, int score) throws SQLException {
        String query = "INSERT INTO results (username, score) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, score);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            showDialog("Error adding Score: " + e.getMessage());
        }
    }
    
    public List<List<String>> getResult(String username) throws SQLException {

        String query = "SELECT * FROM results WHERE username = ?";
        List<List<String>> results = new ArrayList<>();

        try {
            // Prepare the statement
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Process the results
                while (resultSet.next()) {
                    String result_id = resultSet.getString("result_id");
                    String resultUsername = resultSet.getString("username");
                    String score = resultSet.getString("score");
                    String quiz_date = resultSet.getString("quiz_date");

                    List<String> resultsDetails = Arrays.asList(result_id, resultUsername, score, quiz_date);

                    results.add(resultsDetails);
                }
            } catch (SQLException e) {
                showDialog("Error fetching results: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return results;
    }
    
    public void updateLeaderboard() throws SQLException {
        String query = "INSERT INTO leaderboard (username, score) " +
                       "SELECT username, SUM(score) AS total_score " +
                       "FROM results " +
                       "GROUP BY username " +
                       "ON DUPLICATE KEY UPDATE score = VALUES(score)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showDialog("Error updating leaderboard: " + e.getMessage());
        }
    }

    
    public List<List<String>> getLeaderboard() throws SQLException {
        String query = "SELECT username, score FROM leaderboard ORDER BY score DESC";
        List<List<String>> leaderboard = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String score = resultSet.getString("score");

                List<String> leaderboardEntry = Arrays.asList(username, score);
                leaderboard.add(leaderboardEntry);
            }
        } catch (SQLException e) {
            showDialog("Error fetching leaderboard: " + e.getMessage());
        }

        return leaderboard;
    }
    
    public void addHistory(String username) throws SQLException {
        String query = "INSERT INTO history (username) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showDialog("Error adding history: " + e.getMessage());
        }
    }

    public List<List<String>> getHistory(String username) throws SQLException {
        String query = "SELECT time FROM history WHERE username = ? ORDER BY time DESC";
        List<List<String>> historyRecords = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                String timestamp = resultSet.getString("time");
                List<String> historyDetail = Arrays.asList(timestamp);
                historyRecords.add(historyDetail);
            }
        } catch (SQLException e) {
            showDialog("Error fetching history: " + e.getMessage());
        }

        return historyRecords;
    }
    
    public void showDialog(String message) throws SQLException {
        JOptionPane.showMessageDialog(null, message, "Quiz Management", JOptionPane.INFORMATION_MESSAGE);
    }
}
