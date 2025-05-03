package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.service.interfaces.TransactionValidator;

public class TransactionValidatorImpl implements TransactionValidator {
    private static final String RECORD_PATTERN = "[bspr],\\w+,([1-9][0-9]{0,3})$";
    private static final String HEADING = "type,fruit,quantity";
    private static final int INDEX_OF_HEADING = 0;
    private static final int INDEX_OF_QUANTITY_IN_RECORD = 2;

    @Override
    public void validate(String data) {
        checkIfNull(data);
        checkIfEmpty(data);
        String[] lines = data.split(System.lineSeparator());
        checkHeading(lines[INDEX_OF_HEADING]);
        for (int i = 1; i < lines.length; i++) {
            checkIfCorrectFormat(lines[i]);
            checkQuantity(lines[i]);
        }
    }

    private void checkHeading(String data) {
        if (data.length() == 0) {
            throw new InvalidDataException("The heading must not be empty!");
        }
        if (!data.equals(HEADING)) {
            throw new InvalidDataException("The heading is not in correct format!");
        }
    }

    private void checkIfNull(String data) {
        if (data == null) {
            throw new InvalidDataException("The input data must not be null!");
        }
    }

    private void checkIfEmpty(String data) {
        if (data.length() == 0) {
            throw new InvalidDataException("The input data must not be empty!");
        }
    }

    private void checkIfCorrectFormat(String data) {
        String regex = RECORD_PATTERN;
        data = data.trim();
        if (!data.matches(regex)) {
            throw new InvalidDataException("The record doesn't have required format!" + data);
        }
    }

    private void checkQuantity(String data) {
        if (Integer.parseInt(splittedData(data)[INDEX_OF_QUANTITY_IN_RECORD]) <= 0) {
            throw new InvalidDataException("Quantity is not valid! It has to be greater than 0!");
        }
    }

    private String[] splittedData(String data) {
        return data.split(",");
    }
}
