package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> convertedTransaction = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) { // Skip header
            String[] parts = data.get(i).split(",");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid number of fields in row: "
                        + data.get(i));
            }

            FruitTransaction.Operation operation;
            try {
                operation = mapOperation(parts[0]);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid operation code in row: "
                        + data.get(i), e);
            }

            String fruit = parts[1];
            int quantity;
            try {
                quantity = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid quantity for fruit '"
                        + fruit + "': " + parts[2]);
            }

            convertedTransaction.add(new FruitTransaction(operation, fruit, quantity));
        }
        return convertedTransaction;
    }

    private FruitTransaction.Operation mapOperation(String code) {
        return switch (code) {
            case "b" -> FruitTransaction.Operation.BALANCE;
            case "s" -> FruitTransaction.Operation.SUPPLY;
            case "p" -> FruitTransaction.Operation.PURCHASE;
            case "r" -> FruitTransaction.Operation.RETURN;
            default -> throw new IllegalArgumentException("Unknown operation code: " + code);
        };
    }
}
