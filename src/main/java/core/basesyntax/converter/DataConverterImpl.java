package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int EXPECTED_FIELDS_LENGTH = 3;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String COMMA = ",";

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        List<FruitTransaction> transactions = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) {
            String line = data.get(i).trim();
            if (line.isEmpty()) {
                throw new RuntimeException("Empty line at position " + (i + 1));
            }
            String[] fields = line.split(COMMA);
            if (fields.length != EXPECTED_FIELDS_LENGTH) {
                throw new RuntimeException("Incorrect data format at line " + (i + 1));
            }
            Operation operation = mapToOperation(fields[OPERATION_INDEX]);
            FruitTransaction transaction = getFruitTransaction(fields, i, operation);
            transactions.add(transaction);
        }
        return transactions;
    }

    private static FruitTransaction getFruitTransaction(String[] fields,
                                                        int i, Operation operation) {
        String fruit = fields[FRUIT_INDEX];
        int quantity;
        try {
            quantity = Integer.parseInt(fields[QUANTITY_INDEX]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid quantity at line " + (i + 1));
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative at line "
                    + (i + 1));
        }
        return new FruitTransaction(operation, fruit, quantity);
    }

    private Operation mapToOperation(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Operation code cannot be null");
        }
        code = code.toLowerCase();
        return switch (code) {
            case "b" -> Operation.BALANCE;
            case "s" -> Operation.SUPPLY;
            case "p" -> Operation.PURCHASE;
            case "r" -> Operation.RETURN;
            default -> throw new IllegalArgumentException("Unknown operation code: " + code);
        };
    }
}
