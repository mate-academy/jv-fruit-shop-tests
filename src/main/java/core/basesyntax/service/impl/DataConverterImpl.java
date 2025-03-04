package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String COMMA = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputFromCsv) {
        if (inputFromCsv == null) {
            throw new IllegalArgumentException("Input list cannot be null.");
        }
        return inputFromCsv.stream()
                .map(line -> {
                    if (line == null) {
                        throw new IllegalArgumentException("List contains a null element.");
                    }
                    return getFromCsvRow(line);
                })
                .toList();
    }

    private FruitTransaction getFromCsvRow(String line) {
        String[] fields = line.split(COMMA);
        if (fields.length < 3) {
            throw new IllegalArgumentException("Invalid CSV format: " + line);
        }
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation
                .getByCode(fields[OPERATION_INDEX]));
        fruitTransaction.setFruit(fields[FRUIT_INDEX]);
        if (Integer.parseInt(fields[QUANTITY_INDEX]) < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        fruitTransaction.setQuantity(Integer.parseInt(fields[QUANTITY_INDEX]));
        return fruitTransaction;
    }
}
