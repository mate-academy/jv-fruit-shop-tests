package core.basesyntax.service.impl;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {

    private static final String COMMA_LINE_SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int FRUIT_QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parse(List<String> data) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        for (String line: data) {
            String[] lineInfo = line.split(COMMA_LINE_SEPARATOR);
            FruitTransaction.Operation operation =
                    FruitTransaction.Operation.getOperationByCode(lineInfo[OPERATION_INDEX]);
            FruitTransaction fruit = new FruitTransaction(operation,
                    lineInfo[FRUIT_NAME_INDEX], Integer.parseInt(lineInfo[FRUIT_QUANTITY_INDEX]));
            fruitTransactionList.add(fruit);
        }
        return fruitTransactionList;
    }
}
