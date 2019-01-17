package ru.pussy_penetrator.response;

public class AuthResponse {
    private String status;
    private ErrorResponse error;
    private String token;

    public AuthResponse() {}

    public AuthResponse(StatusResponse status) {
        this.status = status.get();
    }

    public AuthResponse(ErrorCode code, String message) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public enum ErrorCode {
        INCORRECT_CREDENTIALS(100),
        LOGIN_VALIDATION(101),
        PASSWORD_VALIDATION(102),
        USER_EXISTS(103),
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
