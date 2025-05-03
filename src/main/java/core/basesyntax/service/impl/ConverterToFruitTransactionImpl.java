package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ConvertToFruitTransactionService;
import java.util.ArrayList;
import java.util.List;

public class ConverterToFruitTransactionImpl implements ConvertToFruitTransactionService {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> convert(List<String> rawData) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (int i = 1; i < rawData.size(); i++) {
            String[] csvFields = rawData.get(i).split(SEPARATOR);
            Operation operation = Operation.getOperationFromCode(csvFields[OPERATION_INDEX]);
            String fruit = csvFields[FRUIT_INDEX];
            int quantity = Integer.parseInt(csvFields[QUANTITY_INDEX]);
            if (quantity < 0) {
                throw new RuntimeException("Quantity can't be less then 0!");
            }
            fruitTransactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return fruitTransactions;
    }
}
