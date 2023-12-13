package core.basesyntax.exceptions;

import java.io.IOException;

public class PathDoesNotExistException extends IOException {
    public PathDoesNotExistException(String message) {
        super(message);
    }
}
