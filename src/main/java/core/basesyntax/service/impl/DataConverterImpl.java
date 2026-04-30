package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        if (data == null) {
            throw new RuntimeException("Input data cannot be null");
        }

        List<FruitTransaction> result = new ArrayList<>();

        for (String line : data) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");

            if (parts.length != 3) {
                throw new RuntimeException("Invalid line format: " + line);
            }

            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(parseOperation(parts[0]));
            transaction.setFruit(parts[1]);
            transaction.setQuantity(Integer.parseInt(parts[2]));

            result.add(transaction);
        }

        return result;
    }

    private FruitTransaction.Operation parseOperation(String code) {
        switch (code) {
            case "b":
                return FruitTransaction.Operation.BALANCE;
            case "s":
                return FruitTransaction.Operation.SUPPLY;
            case "p":
                return FruitTransaction.Operation.PURCHASE;
            case "r":
                return FruitTransaction.Operation.RETURN;
            default:
                throw new RuntimeException("Unknown operation: " + code);
        }
    }
}
