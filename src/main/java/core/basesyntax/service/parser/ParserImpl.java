package core.basesyntax.service.parser;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    private static final int OPERATION = 0;
    private static final int FRUIT = 1;
    private static final int QUANTITY = 2;
    private static final String COMMA = ",";
    private static final String HEADER = "type,fruit,quantity";
    private static final String NULL_LINES_EXCEPTION_MESSAGE = "Lines can't be null";
    private static final String EMPTY_LIST_EXCEPTION_MESSAGE = "List can't be empty";

    public List<FruitTransaction> parseStringToFruitTransaction(List<String> allLines) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        if (allLines == null) {
            throw new IllegalArgumentException(NULL_LINES_EXCEPTION_MESSAGE);
        }
        if (allLines.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_LIST_EXCEPTION_MESSAGE);
        }
        for (String line : allLines) {
            if (line.equals(HEADER)) {
                continue;
            }
            FruitTransaction fruitTransaction = new FruitTransaction();
            String[] data = line.split(COMMA);
            fruitTransaction.setOperation(FruitTransaction.Operation.findByCode(data[OPERATION]));
            fruitTransaction.setFruit(data[FRUIT]);
            fruitTransaction.setQuantity(Integer.parseInt(data[QUANTITY]));
            fruitTransactionList.add(fruitTransaction);
        }
        return fruitTransactionList;
    }
}
