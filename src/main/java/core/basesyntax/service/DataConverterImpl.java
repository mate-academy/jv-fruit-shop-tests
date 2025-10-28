package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> lines) {
        if (lines == null) {
            throw new IllegalArgumentException("Input lines list cannot be null");
        }

        List<FruitTransaction> transactions = new ArrayList<>();

        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Input file is empty");
        }

        int startIndex = 0;
        if (!lines.isEmpty() && lines.get(0).toLowerCase().startsWith("type,fruit,quantity")) {
            startIndex = 1; // pomiń nagłówek
        }

        for (int i = startIndex; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line == null || line.trim().isEmpty()) {
                throw new IllegalArgumentException("Line " + i + " is empty");
            }

            String[] parts = line.split(",");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid CSV format at line: " + line);
            }

            String operationType = parts[0].trim().toLowerCase();
            String fruit = parts[1].trim();
            String quantityString = parts[2].trim();

            if (operationType.isEmpty() || fruit.isEmpty() || quantityString.isEmpty()) {
                throw new IllegalArgumentException("Missing data at line: " + line);
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number at line: " + line, e);
            }

            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative at line: " + line);
            }

            FruitTransaction.Operation operation =
                    FruitTransaction.Operation.fromCode(operationType);

            FruitTransaction transaction =
                    new FruitTransaction(operation, fruit, quantity);
            transactions.add(transaction);
        }

        return transactions;
    }
}
