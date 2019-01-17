package ru.pussy_penetrator.response;

import ru.pussy_penetrator.model.UserPreview;

import java.util.List;

public class UserListResponse {
    private String status;
    private ErrorResponse error;
    private List<UserPreview> response;

    public UserListResponse() {}

    public UserListResponse(List<UserPreview> users) {
        status = StatusResponse.SUCCESS.get();
        response = users;
    }

    public UserListResponse(ErrorCode code, String message) {
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

    public List<UserPreview> getResponse() {
        return response;
    }

    public void setResponse(List<UserPreview> response) {
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
