package Application.Services;

import static Application.Services.ValidationUser.*;

public class MessagesGenerator {
    public static String SigningMessages(int validationStatus) {
        return switch (validationStatus) {
            case NULL_EMAIL -> "Empty, not a valid empty Email";
            case INVALID_EMAIL -> "Invalid email address";
            case EMAIL_NOT_EXIST -> "Email address is already in use";
            case INVALID_PASSWORD -> "Invalid password";
            case VALID -> "Successful Registration, you are welcome";
            default -> "Unknown validation status: " + validationStatus;
        };
    }
}
