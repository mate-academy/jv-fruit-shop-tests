package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    public static final String COMMA_SEPARATOR = ",";
    private static final int QUANTITY_INDEX = 2;
    private static final int FRUIT_INDEX = 1;
    private static final int OPERATION_CODE_INDEX = 0;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> transactionFile) {
        return transactionFile.stream()
                .skip(1)
                .map(l -> l.split(COMMA_SEPARATOR))
                .map(this::validateRowFormat)
                .map(this::parseTransaction)
                .toList();
    }

    private String[] validateRowFormat(String[] arr) {
        if (arr.length != 3) {
            throw new IllegalArgumentException("Invalid CSV row format: " + Arrays.toString(arr));
        }
        return arr;
    }

    private FruitTransaction parseTransaction(String[] fields) {
        int quantity = parseQuantity(fields[QUANTITY_INDEX]);
        return new FruitTransaction(
                FruitTransaction.Operation.fromCode(fields[OPERATION_CODE_INDEX]),
                fields[FRUIT_INDEX],
                quantity
        );
    }

    private int parseQuantity(String quantityStr) {
        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative: " + quantity);
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in CSV: " + quantityStr);
        }
    }
}
