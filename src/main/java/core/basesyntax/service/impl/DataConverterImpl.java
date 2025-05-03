package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int DATA_LENGTH = 3;

    @Override
    public List<FruitTransaction> convert(List<String> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Invalid data entered");
        }
        List<FruitTransaction> transactionList = new ArrayList<>();
        for (String transaction : transactions) {
            String[] parts = transaction.split(",");
            if (parts.length != DATA_LENGTH) {
                throw new ArrayIndexOutOfBoundsException("Invalid data entered");
            }
            Operation operation = new OperationStrategyImpl().getOperationFromCode(parts[0]);
            String fruit = parts[1];
            int quantity;
            try {
                quantity = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new ArrayIndexOutOfBoundsException("Invalid data entered: "
                        + "quantity is not a number");
            }
            transactionList.add(new FruitTransaction(operation, fruit, quantity));
        }
        return transactionList;
    }
}
