package core.basesyntax.service.impl;

import core.basesyntax.service.DataValidation;
import java.util.List;

public class DataValidationImpl implements DataValidation {
    private static final String VALID_REGEX = "[bspr],[a-z]+,[0-9]+";

    @Override
    public boolean checkListNotEmpty(List<String> lines) {
        if (!lines.isEmpty()) {
            return true;
        }
        throw new RuntimeException("Empty lines.");
    }

    @Override
    public boolean checkLine(String line) {
        if (line.matches(VALID_REGEX)) {
            return true;
        }
        throw new RuntimeException("Invalid data in line " + line);
    }

    @Override
    public boolean removeCheck(int currentQuantity, int desiredQuantity) {
        if (currentQuantity < desiredQuantity) {
            throw new RuntimeException("Can't remove more than: " + currentQuantity);
        }
        return true;
    }
}
