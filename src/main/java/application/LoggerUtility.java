package application;

import java.util.logging.*;

public class LoggerUtility {
    private static Logger logger;

    private LoggerUtility() {}

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(LoggerUtility.class.getName());
            setupLogger();
        }
        return logger;
    }

    private static void setupLogger() {
        logger.setUseParentHandlers(false);
        SimpleFormatter simpleFormatter = new SimpleFormatter() {
            private static final String ANSI_RESET = "\u001B[0m";
            private static final String ANSI_BLUE = "\u001B[34m";
            private static final String ANSI_RED = "\u001B[31m";
            private static final String ANSI_GREEN = "\u001B[32m";

            @Override
            public synchronized String format(LogRecord logRecord) {
                String color = ANSI_GREEN; // Default to green for all other levels
                if (logRecord.getLevel() == Level.INFO) {
                    color = ANSI_BLUE; // Blue for INFO
                } else if (logRecord.getLevel() == Level.SEVERE) {
                    color = ANSI_RED; // Red for SEVERE
                }
                return color + logRecord.getMessage() + ANSI_RESET;
            }
        };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL); // Capture all log levels
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
    }
}
