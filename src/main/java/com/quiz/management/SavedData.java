package com.quiz.management;

import java.util.prefs.Preferences;

public class SavedData {

    // Create a Preferences instance
    private final Preferences prefs;

    public SavedData() {
        // Get the preferences for this package
        prefs = Preferences.userNodeForPackage(SavedData.class);
    }

    // Method to save a preference
    public void savePreference(String key, String value) {
        prefs.put(key, value); // Save the string value with the specified key
    }

    // Method to retrieve a preference
    public String getPreference(String key) {
        return prefs.get(key, "default_value"); // Retrieve the value associated with the key
    }

    // Method to remove a preference
    public void removePreference(String key) {
        prefs.remove(key); // Remove the specified key
    }
}

