package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserServiceImpl implements TransactionParserService {
    private static final String COMMA = ",";
    private static final int TITLE_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int FIXED_RECORD_LENGTH = 3;

    @Override
    public List<FruitTransaction> parse(List<String> dataFromFile) {
        List<String> dataCopy = new ArrayList<>(dataFromFile);
        dataCopy.remove(TITLE_INDEX);
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (String data : dataCopy) {
            String[] dataArray = data.split(COMMA);
            if (dataArray.length < 3) {
                throw new IllegalArgumentException(
                        "Wrong record with length: " + dataArray.length + System.lineSeparator()
                                + "Should be: " + FIXED_RECORD_LENGTH);
            }
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation
                    .getOperationByCode(dataArray[OPERATION_INDEX]));
            transaction.setFruit(dataArray[FRUIT_INDEX]);
            transaction.setQuantity(Integer.parseInt(dataArray[QUANTITY_INDEX]));
            fruitTransactions.add(transaction);
        }
        return fruitTransactions;
    }
}
