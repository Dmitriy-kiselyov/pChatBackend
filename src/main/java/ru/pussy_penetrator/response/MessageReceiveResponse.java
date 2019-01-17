package ru.pussy_penetrator.response;

public class MessageReceiveResponse {
    private String status;
    private ErrorResponse error;

    public MessageReceiveResponse() {}

    public MessageReceiveResponse(StatusResponse status) {
        this.status = status.get();
    }

    public MessageReceiveResponse(ErrorCode error) {
        this(StatusResponse.ERROR);
        this.error = new ErrorResponse(error.code(), error.message());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public enum ErrorCode {
        EMPTY_MESSAGE(100, "Пустое сообщение"),
        TOO_LONG_MESSAGE(101, "Сообщение превышает лимит в 500 символов"),
        NO_SUCH_USER(102, "Адресата не существует");

        private final int code;
        private final String message;

        ErrorCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        int code() {
            return code;
        }

        String message() {
            return message;
        }
    }
}
