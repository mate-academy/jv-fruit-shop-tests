package service;

import model.FruitTransaction;

public class CsvParseService implements Parser {
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int QUANTITY_INDEX = 2;
    private static final int LIMIT_RECORD_SIZE = 3;
    private static final String SEPARATOR = ",";

    @Override
    public FruitTransaction parseTransaction(String line) {
        String[] parts = line.split(SEPARATOR);
        if (parts.length != LIMIT_RECORD_SIZE) {
            throw new IllegalArgumentException("Invalid CSV format: " + line);
        }
        return new FruitTransaction(
                parts[FRUIT_NAME_INDEX],
                Integer.parseInt(parts[QUANTITY_INDEX]),
                FruitTransaction.Operation.fromCode(parts[OPERATION_TYPE_INDEX]));
    }
}
