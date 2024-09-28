package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        if (data == null) {
            throw new NullPointerException("Input data cannot be null");
        }
        List<FruitTransaction> transactions = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) {
            String line = data.get(i).trim();
            if (line.isEmpty()) {
                throw new RuntimeException("Empty line at position " + (i + 1));
            }
            String[] fields = line.split(",");
            if (fields.length != 3) {
                throw new RuntimeException("Incorrect data format at line " + (i + 1));
            }
            Operation operation = mapToOperation(fields[0]);
            String fruit = fields[1];
            int quantity;
            try {
                quantity = Integer.parseInt(fields[2]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid quantity at line " + (i + 1));
            }
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative at line "
                        + (i + 1));
            }
            FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
            transactions.add(transaction);
        }
        return transactions;
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
