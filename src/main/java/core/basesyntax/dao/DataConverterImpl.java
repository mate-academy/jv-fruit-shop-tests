package core.basesyntax.dao;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> input) {
        if (input == null || input.isEmpty()) {
            throw new RuntimeException("Input list is empty");
        }
        return input.stream()
                .skip(1)
                .filter(l -> !l.isBlank())
                .map(this::getFromCsvFile)
                .toList();
    }

    public FruitTransaction getFromCsvFile(String input) {
        String[] fields = input.split(",");
        if (fields.length != 3) {
            throw new RuntimeException("Invalid CSV File: " + input);
        }
        String operationRaw = fields[0].trim();
        String fruit = fields[1].trim();
        String quantityRaw = fields[2].trim();

        int quantity;
        try {
            quantity = Integer.parseInt(quantityRaw);
        } catch (NumberFormatException e) {
            throw new RuntimeException(
                    "Invalid quantity in line: " + input, e);
        }

        FruitTransaction.Operation operation =
                FruitTransaction.Operation.fromCode(operationRaw);

        return new FruitTransaction(operation, fruit, quantity);
    }
}
