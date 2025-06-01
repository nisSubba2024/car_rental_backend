package org.example.carrentalbackend.error;

public class ErrorResponse {
    private int statusCode;
    private RequestStatus success;
    private String message;

    public ErrorResponse(int statusCode, RequestStatus success, String message) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public RequestStatus getSuccess() {
        return success;
    }

    public void setSuccess(RequestStatus success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
