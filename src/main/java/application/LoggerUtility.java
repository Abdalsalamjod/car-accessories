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

    public static boolean isInfoEnabled() {
        return getLogger().isLoggable(Level.INFO);
    }

    private static void setupLogger() {
        logger.setUseParentHandlers(false);
        SimpleFormatter simpleFormatter = new SimpleFormatter() {
            private static final String FORMAT_PATTERN = "[%1$tF %1$tT] [%2$s] %3$s %n";

            @Override
            public synchronized String format(LogRecord logRecord) {
                return String.format(FORMAT_PATTERN,
                        logRecord.getMillis(),
                        logRecord.getLevel().getLocalizedName(),
                        logRecord.getMessage());
            }
        };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL); // Set the desired level if you want to filter the log messages
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
    }
}
