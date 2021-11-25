package core.basesyntax.service.impl;

import core.basesyntax.exception.ValidateException;
import core.basesyntax.service.Validator;

public class ValidatorCsvImpl implements Validator {
    private static final int INDEX_OF_ACTION = 0;
    private static final int INDEX_OF_FRUIT_NAME = 1;
    private static final int INDEX_OF_AMOUNT = 2;

    @Override
    public boolean validateLine(String line) {
        if (line == null) {
            throw new ValidateException("Line must not be null");
        }
        String[] fields = line.split(",");

        validateAction(fields[INDEX_OF_ACTION]);
        validateName(fields[INDEX_OF_FRUIT_NAME]);
        validateAmount(fields[INDEX_OF_AMOUNT]);

        return true;
    }

    private boolean validateAction(String action) {
        if (action.equals("")) {
            throw new ValidateException("Action must not be empty");
        }
        if (!(action.equals("b")
                || action.equals("p")
                || action.equals("s")
                || action.equals("r"))) {
            throw new ValidateException("Wrong action: " + action);
        }
        return true;
    }

    private boolean validateName(String name) {
        if (name.equals("")) {
            throw new ValidateException("Name of fruit must not be empty");
        }
        if (!name.matches("[a-zA-Z]+")) {
            throw new ValidateException("Name of fruit must contain only letters: " + name);
        }
        return true;
    }

    private boolean validateAmount(String amount) {
        if (amount.equals("")) {
            throw new ValidateException("Amount of fruit must not be empty");
        }
        if (!amount.matches("[0-9]+")) {
            throw new ValidateException("Amount must contain only numbers");
        }
        if (Integer.parseInt(amount) < 0) {
            throw new ValidateException("Amount must not be negative");
        }
        return true;
    }
}
