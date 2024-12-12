// PrintBuilder.java
// Copyright 2024 maximusf

package util;

/**
 * takes advantage of principle of SOLID
 * Single Responsibility: PrintBuilder is responsible for printing messages
 * Open/Closed: PrintBuilder is open for extension but closed for modification
 * Liskov Substitution: PrintBuilder can be used as a base class for other classes that need to print messages
 * Interface Segregation: PrintBuilder can be used as a base class for other classes that need to print messages
 * Dependency Inversion: PrintBuilder can be used as a base class for other classes that need to print messages
 */

/**
 * Utility class for console output using method chaining
 * Provides a fluent interface for printing formatted messages
 * 
 * Example usage:
 * PrintBuilder.message("Hello").print();                    // Prints: Hello
 * PrintBuilder.message("Invalid input").asError().print();  // Prints: Error: Invalid input
 * PrintBuilder.message("Enter name: ").asPrompt().print();  // Prints: Enter name: (no newline)
 * PrintBuilder.message("Options").asMenu().print();         // Prints: === Wallet-Watch Menu === \n Options
 * PrintBuilder.message("Saved").asSuccess().print();        // Prints: Success: Saved
 */
public class PrintBuilder {
    private final String message;
    private boolean newLine = true;
    private String prefix = "";
    private String suffix = "";
    
    /**
     * Private constructor to enforce use of static factory method
     * @param message The message to be printed
     */
    private PrintBuilder(String message) {
        this.message = message;
    }

    /**
     * Static factory method to create a new PrintBuilder instance
     * Example: PrintBuilder.message("Hello").print();
     * @param message The message to be printed
     * @return A new PrintBuilder instance
     */
    public static PrintBuilder message(String message) {
        return new PrintBuilder(message);
    }

    /**
     * Static method to print menu with multiple options
     * Example: PrintBuilder.menu("Add Income", "View Income", "Exit");
     * Output: === Wallet-Watch Menu ===
     *         1. Add Income
     *         2. View Income
     *         3. Exit
     * @param options Array of menu options
     */
    public static void menu(String... options) {
        System.out.println("\n=== Wallet-Watch Menu ===");
        for (int i = 0; i < options.length; i++) {
            System.out.println(String.format("%d. %s", i + 1, options[i]));
        }
    }

    /**
     * Formats message as an error with "Error: " prefix
     * Example: PrintBuilder.message("Invalid input").asError().print();
     * Output: Error: Invalid input
     * @return The PrintBuilder instance for method chaining
     */
    public PrintBuilder asError() {
        this.prefix = "Error: ";
        return this;
    }

    /**
     * Formats message as a prompt (no newline)
     * Example: PrintBuilder.message("Enter name: ").asPrompt().print();
     * Output: Enter name: (cursor stays on same line)
     * @return The PrintBuilder instance for method chaining
     */
    public PrintBuilder asPrompt() {
        this.newLine = false;
        return this;
    }

    /**
     * Formats message as a success message
     * Example: PrintBuilder.message("Record saved").asSuccess().print();
     * Output: Success: Record saved
     * @return The PrintBuilder instance for method chaining
     */
    public PrintBuilder asSuccess() {
        this.prefix = "Success: ";
        return this;
    }

    /**
     * Adds a custom prefix to the message
     * Example: PrintBuilder.message("Warning").withPrefix("⚠ ").print();
     * Output: ⚠ Warning
     * @param prefix The prefix to add
     * @return The PrintBuilder instance for method chaining
     */
    public PrintBuilder withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Adds a custom suffix to the message
     * Example: PrintBuilder.message("Warning").withSuffix("!").print();
     * Output: Warning!
     * @param suffix The suffix to add
     * @return The PrintBuilder instance for method chaining
     */
    public PrintBuilder withSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * Executes the print operation with all configured formatting
     * Example: PrintBuilder.message("Hello")
     *                     .withPrefix(">> ")
     *                     .withSuffix(" <<")
     *                     .print();
     * Output: >> Hello <<
     */
    public void print() {
        String fullMessage = prefix + message + suffix;
        if (newLine) {
            System.out.println(fullMessage);
        } else {
            System.out.print(fullMessage);
        }
    }
}
