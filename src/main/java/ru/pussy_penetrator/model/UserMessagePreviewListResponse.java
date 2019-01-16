package ru.pussy_penetrator.model;

import java.util.List;

public class UserMessagePreviewListResponse {
    private String status;
    private ErrorResponse error;
    private List<UserMessagePreview> response;

    public UserMessagePreviewListResponse() {}

    public UserMessagePreviewListResponse(List<UserMessagePreview> users) {
        status = StatusResponse.SUCCESS.get();
        response = users;
    }

    public UserMessagePreviewListResponse(ErrorCode code, String message) {
        this.status = StatusResponse.ERROR.get();
        error = new ErrorResponse(code.get(), message);
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

    public List<UserMessagePreview> getResponse() {
        return response;
    }

    public void setResponse(List<UserMessagePreview> response) {
        this.response = response;
    }

    public enum ErrorCode {
        DATABASE_ERROR(110);

        private final int code;

        ErrorCode(int code) {
            this.code = code;
        }

        int get() {
            return code;
        }
    }
}
