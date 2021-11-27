package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import java.util.regex.Pattern;

public class ValidatorImpl implements Validator {
    private static final int OPERATION_INDEX = 0;
    private static final int DEFAULT_STRING_SIZE = 3;
    private static final Pattern PATTERN = Pattern.compile("^[0-9]{1,10}$");
    
    @Override
    public boolean validateLine(String line) {
        String[] lineToBeChecked = line.split(",");
        if (lineToBeChecked.length < DEFAULT_STRING_SIZE) {
            return false;
        }
        if (!lineToBeChecked[OPERATION_INDEX].equals("b")
                && !lineToBeChecked[OPERATION_INDEX].equals("s")
                && !lineToBeChecked[OPERATION_INDEX].equals("p")
                && !lineToBeChecked[OPERATION_INDEX].equals("r")) {
            return false;
        }
        int quantity = 0;
        if (!PATTERN.matcher(lineToBeChecked[2]).matches()) {
            return false;
        }
        return quantity == Math.abs(quantity) ? true : false;
    }
}
