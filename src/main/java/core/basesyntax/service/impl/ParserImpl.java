package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    private static final int OPERATION_POSITION = 0;
    private static final int FRUIT_POSITION = 1;
    private static final int AMOUNT_POSITION = 2;
    private static final int LINE_ELEMENTS = 3;

    @Override
    public List<FruitTransaction> parseInput(List<String> fileContent) {
        if (fileContent == null || fileContent.isEmpty()) {
            throw new RuntimeException("Invalid input!");
        }
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        FruitTransaction fruitTransaction;

        for (String line : fileContent) {
            String[] lineSplit = line.split(",");
            if (lineSplit.length == LINE_ELEMENTS) {
                fruitTransaction = new FruitTransaction(
                        Operation.valueOfCode(lineSplit[OPERATION_POSITION]),
                        lineSplit[FRUIT_POSITION],
                        Integer.parseInt(lineSplit[AMOUNT_POSITION]));
                fruitTransactions.add(fruitTransaction);
            }
        }
        if (fruitTransactions.isEmpty()) {
            throw new RuntimeException("Invalid input!");
        }

        return fruitTransactions;
    }
}
