package org.rest.teamapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    /**
     * The HTTP status code of the error.
     */
    private int status;
    private String message;
    private long timestamp;

    /**
     * Constructs an ErrorResponse with the specified status and message.
     *
     * @param status  The HTTP status code.
     * @param message The error message.
     */
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
