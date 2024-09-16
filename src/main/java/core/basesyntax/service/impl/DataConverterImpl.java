package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : data) {
            String[] parts = line.split(",");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid data format: " + line);
            }
            FruitTransaction.Operation operation = FruitTransaction.Operation
                        .valueOf(parts[0].trim().toUpperCase());
            String fruit = parts[1];
            int quantity = Integer.parseInt(parts[2]);
            if (quantity > 0) {
                transactions.add(new FruitTransaction(operation, fruit, quantity));
            } else {
                throw new RuntimeException("Invalid quantity: " + quantity);
            }
        }
        return transactions;
    }
}
