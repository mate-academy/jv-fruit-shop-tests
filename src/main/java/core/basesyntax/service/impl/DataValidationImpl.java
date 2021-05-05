package core.basesyntax.service.impl;

import core.basesyntax.service.DataValidation;
import java.util.List;

public class DataValidationImpl implements DataValidation {
    private static final String VALID_RECORD_REGEX = "[bspr],[a-z]+,[0-9]+";

    @Override
    public boolean checkListNotEmpty(List<String> records) {
        if (!records.isEmpty()) {
            return true;
        }
        throw new RuntimeException("Invalid input information: empty records");
    }

    @Override
    public boolean checkLine(String line) {
        if (line.matches(VALID_RECORD_REGEX)) {
            return true;
        }
        throw new RuntimeException("Invalid input information in line " + line);
    }

    @Override
    public boolean subtractCheck(int currentQuantity, int subtract) {
        if (currentQuantity < subtract) {
            throw new RuntimeException("Can't subtract more than current quantity");
        }
        return true;
    }
}
