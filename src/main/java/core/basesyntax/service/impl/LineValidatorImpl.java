package core.basesyntax.service.impl;

import core.basesyntax.service.LineValidator;

public class LineValidatorImpl implements LineValidator {
    private static final String VALIDATION_REGEX = "[bspr],\\w+,\\d+";

    @Override
    public boolean isValid(String line) {
        return line.matches(VALIDATION_REGEX);
    }
}
