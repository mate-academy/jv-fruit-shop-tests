package core.basesyntax.service.impl;

import core.basesyntax.exception.ValidatorException;
import core.basesyntax.service.Validator;
import java.util.List;
import java.util.regex.Pattern;

public class ValidatorImpl implements Validator {
    private static final String INPUT_TITLE = "type,fruit,quantity";
    private static final String PATTERN_LINE = "[bprs],[a-z]*,[0-9]*";

    @Override
    public void validate(List<String> lines) {
        if (lines.isEmpty()) {
            throw new ValidatorException("File is empty, please try another one");
        }
        for (String line : lines) {
            if (!Pattern.matches(PATTERN_LINE, line) && !line.equals(INPUT_TITLE)) {
                throw new ValidatorException("Invalid input data, try again");
            }
        }
    }
}
