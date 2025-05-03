package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String DELIMITER = ",";
    private static final int OFFSET = 1;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputList) {
        if (inputList == null || inputList.isEmpty()) {
            throw new RuntimeException("Error reading from input list");
        }
        return inputList.stream()
                .skip(OFFSET)
                .map(s -> {
                    String[] parts = s.split(DELIMITER);
                    checkInputData(parts);
                    return new FruitTransaction(
                            FruitTransaction.Operation.getFromCode(parts[OPERATION_INDEX]),
                            parts[FRUIT_INDEX],
                            Integer.parseInt(parts[QUANTITY_INDEX])
                    );
                })
                .toList();
    }

    private void checkInputData(String[] parts) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid input data");
        }
        try {
            Integer.parseInt(parts[QUANTITY_INDEX]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quantity value");
        }
    }
}
