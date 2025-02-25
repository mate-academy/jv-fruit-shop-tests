package core.basesyntax.impl;

import core.basesyntax.service.ProcessData;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.List;

public class ProcessDataImpl implements ProcessData {
    private static final String COMMA = ",";
    private static final int HEAD_INFO_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int INDEX_OF_AMOUNT = 2;
    private static final int VALID_LENGTH = 3;
    private static final int MIN_ALLOWED_QUANTITY = 0;

    @Override
    public List<FruitTransaction> process(List<String> fileLines) {
        return fileLines.stream()
                .skip(HEAD_INFO_INDEX)
                .map(this::convertDataToTransaction)
                .toList();
    }

    private FruitTransaction convertDataToTransaction(String inputDataLine) {

        if (inputDataLine == null || inputDataLine.isEmpty()) {
            throw new IllegalArgumentException("The entrance row cannot be empty");
        }
        String[] data = inputDataLine.split(COMMA);
        if (data.length != VALID_LENGTH) {
            throw new IllegalArgumentException("Invalid input data format: "
                    + inputDataLine);
        }
        if (data[OPERATION_INDEX].isEmpty()
                || data[FRUIT_TYPE_INDEX].isEmpty()
                || data[INDEX_OF_AMOUNT].isEmpty()) {
            throw new IllegalArgumentException("Data contains empty values: "
                    + inputDataLine);
        }
        int quantity;
        try {
            quantity = Integer.parseInt(data[INDEX_OF_AMOUNT]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a valid number: "
                    + data[INDEX_OF_AMOUNT]);
        }
        if (quantity < MIN_ALLOWED_QUANTITY) {
            throw new IllegalArgumentException("Quantity cannot be negative: " + quantity);
        }
        return new FruitTransaction(Operation.getOperationCode(data[OPERATION_INDEX]),
                data[FRUIT_TYPE_INDEX], quantity);
    }
}
