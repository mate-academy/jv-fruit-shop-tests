package core.basesyntax.service.implementation;

import core.basesyntax.service.Validator;
import java.util.regex.Pattern;

public class ValidatorImpl implements Validator {
    private static final String LINE_MATCHER_REGEX = "[bprs],[a-z]*,[0-9]*";
    private static final int NUMBER_OF_PARAMETERS = 3;

    @Override
    public boolean validate(String line) {
        String[] oneLineData = line.split(",");
        if (oneLineData.length != NUMBER_OF_PARAMETERS) {
            return false;
        }
        if (!Pattern.matches(LINE_MATCHER_REGEX, line)) {
            return false;
        }
        return true;
    }
}
