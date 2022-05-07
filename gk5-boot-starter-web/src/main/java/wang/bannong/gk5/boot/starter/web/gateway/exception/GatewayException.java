package wang.bannong.gk5.boot.starter.web.gateway.exception;

/**
 * gateway exception
 */
public class GatewayException extends RuntimeException {
    private static final long serialVersionUID = -8385992022648527148L;

    public GatewayException() {
        super();
    }

    public GatewayException(String message) {
        super(message);
    }

    public GatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public GatewayException(Throwable cause) {
        super(cause);
    }

    protected GatewayException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
