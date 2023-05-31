package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;

public class FruitTransactionParserImpl implements FruitTransactionParser {
    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parse(List<String> transactions) {
        if (transactions.size() == 0) {
            throw new RuntimeException("File don't have date");
        }
        transactions.remove(0);
        List<FruitTransaction> resultList = new ArrayList<>();
        for (String transaction: transactions) {
            String[] splitLine = transaction.split(",");
            if (splitLine.length != 3) {
                throw new RuntimeException("Date in file have incorrect format");
            }
            if (Integer.parseInt(splitLine[QUANTITY_INDEX]) < 0) {
                throw new RuntimeException("Quantity can't been negative");
            }
            FruitTransaction.Operation operation =
                    FruitTransaction.Operation.getOperationFromString(splitLine[OPERATION_INDEX]);
            String fruit = splitLine[FRUIT_INDEX];
            int quantity = Integer.parseInt(splitLine[QUANTITY_INDEX]);
            resultList.add(new FruitTransaction(operation, fruit, quantity));
        }
        return resultList;
    }
}
