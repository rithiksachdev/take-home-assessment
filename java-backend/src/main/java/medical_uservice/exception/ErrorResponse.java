package medical_uservice.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;
    private String error;

    public ErrorResponse(String message,  String error) {
        this.message = message;
        this.error = error;
    }
}
