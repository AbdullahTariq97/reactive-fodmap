package uk.sky.fodmap.exception;

import lombok.Data;

@Data
public class DownstreamFailureException extends RuntimeException {

    public DownstreamFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
