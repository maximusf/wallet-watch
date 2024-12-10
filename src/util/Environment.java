// Environment.java
// Copyright 2024 maximusf

// Loads environment variables from a .env file

package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads and manages environment variables from .env file
 */
public class Environment {
    // Store environment variables in memory
    private static final Map<String, String> VARIABLES = new HashMap<>();

    // Load variables when class is first used
    static {
        try {
            System.out.println("Loading .env file...");
            Files.lines(Paths.get(".env")).forEach(line -> {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    VARIABLES.put(parts[0], parts[1]);
                }
            });
            System.out.println("Environment variables loaded successfully");
        } catch (IOException e) {
            System.err.println("Error loading .env file: " + e.getMessage());
        }
    }

    /**
     * Get value of environment variable
     * @param key Name of environment variable
     * @return Value of environment variable, or null if not found
     */
    public static String get(String key) {
        return VARIABLES.get(key);
    }
}
