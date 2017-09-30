package net.meerkat.risk.position;

/**
 *
 * @author Ollie
 */
public class UnknownPositionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnknownPositionException(final PositionId positionId) {
        super("Unknown position: " + positionId);
    }

    public UnknownPositionException(final String message) {
        super(message);
    }

}