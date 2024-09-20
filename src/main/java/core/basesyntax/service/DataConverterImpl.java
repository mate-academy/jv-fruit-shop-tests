package core.basesyntax.service;

import core.basesyntax.DataConverter;
import core.basesyntax.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int INDEX_OF_TYPE_CODE = 0;
    private static final int INDEX_OF_FRUIT = 1;
    private static final int INDEX_OF_QUANTITY = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        if (data == null) {
            throw new RuntimeException("Input data is null");
        } else if (data.isEmpty()) {
            throw new RuntimeException("Input list is empty");
        }

        List<FruitTransaction> transactions = new ArrayList<>();

        for (String line : data) {
            String[] parts = line.split(";");
            String typeCode = parts[INDEX_OF_TYPE_CODE].trim();
            String fruit = parts[INDEX_OF_FRUIT].trim();
            int quantity;
            try {
                quantity = Integer.parseInt(parts[INDEX_OF_QUANTITY]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid quantity: " + parts[INDEX_OF_QUANTITY], e);
            }
            FruitTransaction.Operation operation = FruitTransaction.fromCode(typeCode);
            FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, quantity);
            transactions.add(fruitTransaction);
        }
        return transactions;
    }
}
