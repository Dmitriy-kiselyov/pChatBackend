package ru.pussy_penetrator.response;

import java.util.List;

public class MessageListResponse {
    private String status;
    private ErrorResponse error;
    private List<MessageResponse> response;

    public MessageListResponse() {}

    public MessageListResponse(List<MessageResponse> users) {
        status = StatusResponse.SUCCESS.get();
        response = users;
    }

    public MessageListResponse(ErrorCode error) {
        status = StatusResponse.ERROR.get();
        this.error = new ErrorResponse(error.code(), error.message());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MessageResponse> getResponse() {
        return response;
    }

    public void setResponse(List<MessageResponse> response) {
        this.response = response;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public enum ErrorCode {
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
