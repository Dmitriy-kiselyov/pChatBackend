package ru.pussy_penetrator.util;

import com.sun.istack.internal.Nullable;

public class UserValidator {
    private static final int LOGIN_MIN_LENGTH = 4;
    private static final int LOGIN_MAX_LENGTH = 20;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 50;

    @Nullable
    public static LoginValidationError validateLogin(@Nullable String login) {
        if (login == null || login.isEmpty()) {
            return LoginValidationError.EMPTY;
        }

        if (!login.matches("^[a-zA-Z0-9]+$")) {
            return LoginValidationError.SIGN;
        }

        if (login.length() < LOGIN_MIN_LENGTH || login.length() > LOGIN_MAX_LENGTH) {
            return LoginValidationError.LENGTH;
        }

        char firstChar = login.charAt(0);
        if (!(firstChar >= 'a' && firstChar <= 'z' || firstChar >= 'A' && firstChar <= 'Z')) {
            return LoginValidationError.FIRST_LETTER;
        }

        return null;
    }

    @Nullable
    public static PasswordValidationError validatePassword(@Nullable String password) {
        if (password == null || password.isEmpty()) {
            return PasswordValidationError.EMPTY;
        }

        if (!password.matches("^[a-zA-Z0-9_-]+$")) {
            return PasswordValidationError.SIGN;
        }

        if (password.length() < UserValidator.PASSWORD_MIN_LENGTH || password.length() > UserValidator.PASSWORD_MAX_LENGTH) {
            return PasswordValidationError.LENGTH;
        }

        return null;
    }

    public enum LoginValidationError {
        EMPTY("Поле обязательно к заполнению"),
        SIGN("Логин может состоять только из английских букв и цифр"),
        FIRST_LETTER("Логин должен начинаться на букву"),
        LENGTH("Логин должен быть не менее " + UserValidator.LOGIN_MIN_LENGTH + " и не более " + UserValidator.LOGIN_MAX_LENGTH + " символов");

        private String message;

        LoginValidationError(String message) {
            this.message = message;
        }

        public String get() {
            return message;
        }
    }

    public enum PasswordValidationError {
        EMPTY("Поле обязательно к заполнению"),
        SIGN("Пароль может состоять только из английских букв, цифр, знаков '_' и '-'"),
        LENGTH("Пароль должен быть не менее " + UserValidator.PASSWORD_MIN_LENGTH + " и не более " + UserValidator.PASSWORD_MAX_LENGTH + " символов");

        private String message;

        PasswordValidationError(String message) {
            this.message = message;
        }

        public String get() {
            return message;
        }
    }
}
