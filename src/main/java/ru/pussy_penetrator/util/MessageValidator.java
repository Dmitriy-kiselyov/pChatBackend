package ru.pussy_penetrator.util;

import ru.pussy_penetrator.model.Message;
import ru.pussy_penetrator.response.MessageReceiveResponse.ErrorCode;

public class MessageValidator {
    public static ErrorCode validate(Message message) {
        String text = message.getText();
        if (text == null) {
            return ErrorCode.EMPTY_MESSAGE;
        }
        if (text.isEmpty()) {
            return ErrorCode.EMPTY_MESSAGE;
        }
        if (text.length() > 500) {
            return ErrorCode.TOO_LONG_MESSAGE;
        }

        return null;
    }
}
