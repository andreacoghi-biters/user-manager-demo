package it.biters.demo.user.manager.exceptions;

public class UserNotFoundException extends Exception {

    private static final String MESSAGE_FORMAT = "User '%s' not found!";

    public UserNotFoundException(long id) {
        super(String.format(MESSAGE_FORMAT, id));
    }
}
