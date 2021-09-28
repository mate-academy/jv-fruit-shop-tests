package core.basesyntax.service.impl;

import core.basesyntax.service.InputDataValidator;
import java.util.List;
import java.util.regex.Pattern;

public class InputDataValidatorImpl implements InputDataValidator {
    private static final Pattern VALID_RECORD = Pattern.compile("[sprb],[a-zA-Z]+,[0-9]+");

    @Override
    public boolean chekDate(List<String> str) {
        for (int i = 1; i < str.size(); i++) {
            if (!VALID_RECORD.matcher(str.get(i)).matches()) {
                throw new RuntimeException("Input data is wrong!");
            }
        }
        return true;
    }
}
