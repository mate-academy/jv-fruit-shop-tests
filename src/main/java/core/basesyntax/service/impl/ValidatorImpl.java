package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;

public class ValidatorImpl implements Validator {
    private static final String VALID_FORMAT = "[sprb],[a-zA-Z]+,[0-9]+";

    public boolean isLineValid(String string) {
        if (string == null) {
            throw new RuntimeException("String cam't be null");
        }
        return string.matches(VALID_FORMAT);
    }
}
