package core.basesyntax.converter;

import core.basesyntax.transaction.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> converterToTransaction(List<String> inputData) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (int i = 1; i < inputData.size(); i++) {
            String data = inputData.get(i);
            String[] parts = data.split(",");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Incorrect input data format: " + data);
            }

            String operationCode = parts[0].trim();
            String fruitName = parts[1].trim();

            int quantity;
            try {
                quantity = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid quantity: " + parts[2].trim(), e);
            }

            // Determine the type of activity
            FruitTransaction.Operation operation
                    = FruitTransaction.Operation.fromCode(operationCode);

            // We create a new object and add it to the list
            FruitTransaction transaction = new FruitTransaction(operation, fruitName, quantity);
            fruitTransactions.add(transaction);
        }
        return fruitTransactions;
    }
}
