package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String EXPECTED_LINE_START = "type";
    private static final String DELIMITER = ",";
    private static final int OPERATION_TYPE = 0;
    private static final int FRUIT_NAME = 1;
    private static final int QUANTITY_OF_FRUITS = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : data) {
            if (line.startsWith(EXPECTED_LINE_START)) {
                continue;
            }
            String[] parts = line.split(DELIMITER);
            FruitTransaction.Operation operation =
                    getOperationFromCode(parts[OPERATION_TYPE].trim());
            String fruit = parts[FRUIT_NAME].trim();
            int quantity = Integer.parseInt(parts[QUANTITY_OF_FRUITS].trim());

            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(operation);
            transaction.setFruit(fruit);
            transaction.setQuantity(quantity);

            transactions.add(transaction);
        }
        return transactions;
    }

    private FruitTransaction.Operation getOperationFromCode(String code) {
        for (FruitTransaction.Operation op : FruitTransaction.Operation.values()) {
            if (op.getCode().equals(code)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid operation code: " + code);
    }
}
