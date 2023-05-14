package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;

public class ParserServiceImpl implements ParserService {
    private static final String COMMA_DELIMITER = ",";
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parse(List<String> records) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        FruitTransaction.Operation type;
        String fruit;
        int quantity;
        if (records.size() == 0) {
            throw new WrongFormatException("There is WrongFormatException: ");
        }
        for (String record : records) {
            String[] values = record.split(COMMA_DELIMITER);
            if (values[OPERATION_TYPE_INDEX].equals("type")) {
                continue;
            }
            type = FruitTransaction.Operation.getByCode(values[OPERATION_TYPE_INDEX]);
            fruit = values[FRUIT_INDEX];
            quantity = Integer.parseInt(values[QUANTITY_INDEX]);
            FruitTransaction fruitTransaction = new FruitTransaction(type, fruit, quantity);
            fruitTransactionList.add(fruitTransaction);
        }
        return fruitTransactionList;
    }
}
