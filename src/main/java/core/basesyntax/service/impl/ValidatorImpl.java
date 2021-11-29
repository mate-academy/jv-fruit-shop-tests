package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import java.util.regex.Pattern;

public class ValidatorImpl implements Validator {
    private static final int OPERATION_INDEX = 0;
    private static final int QUANTITY_INDEX = 2;
    private static final int MIN_NUMBER_OF_ARGUMENTS = 3;
    private static final Pattern VALID_QUANTITY_FORMAT = Pattern.compile("^[0-9]{1,10}$");
    
    @Override
    public boolean validateLine(String line) {
        String[] lineToBeChecked = line.split(",");
        if (lineToBeChecked.length < MIN_NUMBER_OF_ARGUMENTS) {
            return false;
        }
        if (!lineToBeChecked[OPERATION_INDEX].equals("b")
                && !lineToBeChecked[OPERATION_INDEX].equals("s")
                && !lineToBeChecked[OPERATION_INDEX].equals("p")
                && !lineToBeChecked[OPERATION_INDEX].equals("r")) {
            return false;
        }
        int quantity = 0;
        if (!VALID_QUANTITY_FORMAT.matcher(lineToBeChecked[QUANTITY_INDEX]).matches()) {
            return false;
        }
        return quantity == Math.abs(quantity) ? true : false;
    }
}
