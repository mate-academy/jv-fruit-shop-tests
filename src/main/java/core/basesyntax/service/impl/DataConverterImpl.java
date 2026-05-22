package core.basesyntax.service.impl;

import core.basesyntax.DataConverter;
import core.basesyntax.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : data) {
            String[] parts = line.split(",");
            FruitTransaction.Operation operation = FruitTransaction.Operation
                    .getOperation(parts[0]);
            String fruit = parts[1];
            int quantity = Integer.parseInt(parts[2].trim());
            FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
            transactions.add(transaction);
        }
        return transactions;
    }
}
