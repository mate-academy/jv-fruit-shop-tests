package service.impl;

import java.util.regex.Pattern;
import service.Validator;

public class ValidatorImpl implements Validator {
    private static final String SEPARATOR = ",";
    private static final int INDEX_OF_QUANTITY = 2;
    private static final String PATTERN_LINE = "[bprs],[a-z]+,[0-9]+";

    @Override
    public boolean validate(String line) {
        if (line.isBlank()) {
            throw new RuntimeException("Invalid File");
        }
        if (!Pattern.matches(PATTERN_LINE, line)) {
            throw new RuntimeException("Invalid input data, try again");
        }
        String [] split = line.split(SEPARATOR);
        int quantity = Integer.parseInt(split[INDEX_OF_QUANTITY]);
        if (quantity < 0 || split.length > 3) {
            throw new RuntimeException("No valid line");
        }
        return true;
    }
}
