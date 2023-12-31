package application;

import java.util.logging.*;

public class LoggerUtility {
    private static final ThreadLocal<Logger> threadLocalLogger = new ThreadLocal<>();

    private LoggerUtility() {}

    public static Logger getLogger() {
        Logger logger = threadLocalLogger.get();
        if (logger == null) {
            logger = Logger.getLogger(LoggerUtility.class.getName());
            setupLogger(logger);
            threadLocalLogger.set(logger);
        }
        return logger;
    }

    private static void setupLogger(Logger logger) {
        logger.setUseParentHandlers(false);
        SimpleFormatter simpleFormatter = new SimpleFormatter() {
            private static final String ANSI_RESET = "\u001B[0m";
            private static final String ANSI_BLUE = "\u001B[34m";
            private static final String ANSI_RED = "\u001B[31m";
            private static final String ANSI_GREEN = "\u001B[32m";

            @Override
            public synchronized String format(LogRecord logRecord) {
                String color = ANSI_GREEN;
                if (logRecord.getLevel() == Level.INFO) {
                    color = ANSI_BLUE;
                } else if (logRecord.getLevel() == Level.SEVERE) {
                    color = ANSI_RED;
                }
                return color + logRecord.getMessage() + ANSI_RESET;
            }
        };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
    }
}
