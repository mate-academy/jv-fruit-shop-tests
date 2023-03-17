package core.basesyntax.serviceimpl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;

public class TransactionParserServiceImpl implements TransactionParserService {
    private static final int COLUMN_QUANTITY_CHECKER = 3;
    private static final String DATA_SEPARATOR = ",";
    private static final int OPERATION_CODE = 0;
    private static final int FRUIT_NAME = 1;
    private static final int FRUIT_QUANTITY = 2;

    @Override
    public FruitTransaction saveToStorage(String line) {
        if (line == null) {
            throw new RuntimeException("Input can`t be null!");
        }
        String[] array = line.split(DATA_SEPARATOR);
        if (array.length != COLUMN_QUANTITY_CHECKER) {
            throw new RuntimeException("Wrong data format in input file!");
        }
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.byCode(array[OPERATION_CODE]));
        fruitTransaction.setFruit(array[FRUIT_NAME]);
        fruitTransaction.setQuantity(Integer.parseInt(array[FRUIT_QUANTITY]));
        return fruitTransaction;
    }
}
