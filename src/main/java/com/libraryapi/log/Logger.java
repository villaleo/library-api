package com.libraryapi.log;

public class Logger {
    private static final String GreenTextColor = "\u001B[32m";
    private static final String YellowTextColor = "\u001B[33m";
    private static final String ResetTextColor = "\u001B[0m";
    private static final String CyanTextColor = "\u001B[36m";

    /**
     * A call to this method is essentially a call to <code>info</code> with a formatted message
     * @param message A message with format string placeholders
     * @param args Arguments to supply to the message
     */
    public static void info(String message, Object... args) {
        info(String.format(message, args));
    }

    /**
     * Displays the message prefixed with <code>[INFO]</code> to the client with the message
     * supplied. Output contains a new line character after the message.
     * @param message The message supplied
     */
    public static void info(String message) {
        System.out.printf("\t%s[INFO]%s %s%s\n", GreenTextColor, CyanTextColor, message, ResetTextColor);
    }

    /**
     * A call to this method is essentially a call to <code>warn</code> with a formatted message
     * @param message A message with format string placeholders
     * @param args Arguments to supply to the message
     */
    public static void warn(String message, Object... args) {
        warn(String.format(message, args));
    }

    /**
     * Displays a message prefixed with <code>[WARN]</code> to the client with the message supplied.
     * Output contains a new line character after the message.
     * @param message The message supplied
     */
    public static void warn(String message) {
        System.out.printf("\t%s[WARN]%s %s%s\n", YellowTextColor, CyanTextColor, message, ResetTextColor);
    }
}
