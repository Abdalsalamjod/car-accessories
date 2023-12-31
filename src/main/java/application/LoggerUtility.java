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

            @Override
            public synchronized String format(LogRecord logRecord) {
                String color;
                if (logRecord.getLevel() == Level.INFO) {
                    color = ANSI_BLUE;
                } else if (logRecord.getLevel() == Level.SEVERE) {
                    color = ANSI_RED;
                } else {
                    color = ANSI_RESET;
                }
                return color + logRecord.getMessage() + ANSI_RESET ;
            }
        };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL); // Set the desired level if you want to filter the log messages
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
    }
}
