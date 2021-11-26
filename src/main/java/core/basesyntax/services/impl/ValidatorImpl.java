package core.basesyntax.services.impl;

import core.basesyntax.services.Validator;

public class ValidatorImpl implements Validator {
    private static final String VALID_LINE_PATTERN = "[a-z],\\w+,\\d+";

    @Override
    public boolean validate(String line) {
        return line != null && line.matches(VALID_LINE_PATTERN);
    }
}
