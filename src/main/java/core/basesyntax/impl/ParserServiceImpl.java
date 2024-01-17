package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;

public class ParserServiceImpl implements ParserService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String CSV_SEPARATOR = ",";
    private static final int TRANSACTION_FIELDS_COUNT = 3;

    @Override
    public List<FruitTransaction> parse(List<String> lists) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        Operation type;
        String fruit;
        int quantity;
        if (lists.isEmpty()) {
            throw new RuntimeException("Error parsing transaction: empty file");
        }
        for (String list : lists) {
            String[] values = list.split(CSV_SEPARATOR);
            if (values[OPERATION_INDEX].equals("type")) {
                continue;
            }
            if (values.length != TRANSACTION_FIELDS_COUNT) {
                throw new RuntimeException("Error parsing transaction: " + list);
            }
            type = Operation.getOperationByCode(values[OPERATION_INDEX]);
            fruit = values[FRUIT_INDEX];
            quantity = Integer.parseInt(values[QUANTITY_INDEX]);
            FruitTransaction fruitTransaction = new FruitTransaction(type, fruit, quantity);
            fruitTransactionList.add(fruitTransaction);
        }
        return fruitTransactionList;
    }
}
