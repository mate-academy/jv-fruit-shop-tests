package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ConverterService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterTransactionServiceImpl implements ConverterService {
    private static final int TITLE_POSITION = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<Transaction> convertFromString(List<String> cvsLines) {
        if (cvsLines == null) {
            throw new NullPointerException("Invalid file for converting");
        }
        return cvsLines.stream()
                .skip(TITLE_POSITION)
                .map(s -> s.split(","))
                .map(this::parseToTransaction)
                .collect(Collectors.toList());
    }

    private Transaction parseToTransaction(String[] line) {
        isValid(line);
        String operation = line[OPERATION_INDEX];
        String fruit = line[FRUIT_NAME_INDEX];
        int quantity = Integer.parseInt(line[QUANTITY_INDEX]);
        return new Transaction(Operation.getByCode(operation), fruit, quantity);
    }

    private void isValid(String[] line) {
        if(line.length != 3) {
            throw new RuntimeException("Line " + Arrays.toString(line) + " doesn't consist of 3 elements");
        }
        int quantity;
        try {
            quantity = Integer.parseInt(line[QUANTITY_INDEX]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Can't parse " + line[QUANTITY_INDEX] + " to integer");
        }
        if (quantity < 0) {
            throw new RuntimeException("Quantity can't be less than 0");
        }
    }
}
