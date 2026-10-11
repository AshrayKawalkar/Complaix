package com.Ashray.Smart.Complaint.Management.System.Ai.Exception;

public class AiServiceException extends RuntimeException{
    public AiServiceException(String message) {
        super(message);
    }

    public AiServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
