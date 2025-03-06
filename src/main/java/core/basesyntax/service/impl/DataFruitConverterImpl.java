package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationsList;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataFruitConverterImpl implements DataConverter<FruitTransaction> {
    private static final int OPERATION_POSITION = 0;
    private static final int FRUIT_POSITION = 1;
    private static final int QUANTITY_POSITION = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String[]> data) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            String operationCode = row[OPERATION_POSITION];
            String fruit = row[FRUIT_POSITION];
            int quantity;
            try {
                quantity = Integer.parseInt(row[QUANTITY_POSITION]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Quantity is not a number, please check it", e);
            }
            OperationsList operationsList = OperationsList.fromCode(operationCode);
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(operationsList);
            transaction.setFruit(fruit);
            transaction.setQuantity(quantity);
            fruitTransactions.add(transaction);
        }
        return fruitTransactions;
    }
}
