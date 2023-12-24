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
            private static final String ANSI_BLUE = "\u001B[34m";
            private static final String ANSI_RED = "\u001B[31m";
            private static final String ANSI_RESET = "\u001B[0m";

            @Override
            public synchronized String format(LogRecord logRecord) {
                String color = ANSI_BLUE; // Default to blue

                if (logRecord.getLevel().intValue() >= Level.SEVERE.intValue()) {
                    color = ANSI_RED; // Set to red for severe errors
                }

                // Apply the color based on the log level, and reset after the message
                return color + logRecord.getMessage() + ANSI_RESET ;
            }
        };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL); // Set the desired level if you want to filter the log messages
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
    }
}
