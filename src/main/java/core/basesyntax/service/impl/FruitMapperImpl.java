package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionMapper;
import java.util.ArrayList;
import java.util.List;

public class FruitMapperImpl implements FruitTransactionMapper {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String COMA = ",";

    @Override
    public List<FruitTransaction> map(List<String> dataFromFile) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        if (dataFromFile.isEmpty()) {
            throw new RuntimeException("Can't parse data from empty file");
        }
        for (String data : dataFromFile) {
            String[] record = data.split(COMA);
            String activity = record[OPERATION_INDEX];
            String fruit = record[FRUIT_INDEX];
            int quantity = Integer.parseInt(record[QUANTITY_INDEX]);
            fruitTransactionList.add(new FruitTransaction(
                    Operation.getOperationType(activity),
                    fruit, quantity));
        }
        return fruitTransactionList;
    }
}
