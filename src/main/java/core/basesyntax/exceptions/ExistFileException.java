package core.basesyntax.exceptions;

import java.io.IOException;

public class ExistFileException extends RuntimeException {
    public ExistFileException(String message, IOException e) {
        super(message);
    }
}
