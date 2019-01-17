package ru.pussy_penetrator.model;

public class UnauthorizedResponse {
    private static String status;
    private static ErrorResponse error;

    public UnauthorizedResponse() {
        status = StatusResponse.ERROR.get();
        error = new ErrorResponse(401, "Unauthorized");
    }

    public static String getStatus() {
        return status;
    }

    public static ErrorResponse getError() {
        return error;
    }
}
