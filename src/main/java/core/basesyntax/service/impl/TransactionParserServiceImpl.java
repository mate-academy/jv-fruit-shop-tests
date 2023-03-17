package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserServiceImpl implements TransactionParserService {
    private static final int INDEX_OF_TITLE = 0;
    private static final int OPERATION_POSITION = 0;
    private static final int FRUIT_POSITION = 1;
    private static final int QUANTITY_POSITION = 2;
    private static final String SEPARATOR = ",";

    @Override
    public List<FruitTransaction> parseInputData(List<String> inputData) {
        if (inputData.size() < 1) {
            throw new RuntimeException("there were no title");
        }
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        inputData.remove(INDEX_OF_TITLE);
        for (String line : inputData) {
            String[] data = line.split(SEPARATOR);
            FruitTransaction fruitTransaction = new FruitTransaction(data[OPERATION_POSITION],
                    data[FRUIT_POSITION],
                    Integer.valueOf(data[QUANTITY_POSITION]));
            fruitTransactions.add(fruitTransaction);
        }
        return fruitTransactions;
    }
}
