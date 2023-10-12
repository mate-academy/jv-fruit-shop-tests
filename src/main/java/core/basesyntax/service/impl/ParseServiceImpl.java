package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.ParseService;
import java.util.ArrayList;
import java.util.List;

public class ParseServiceImpl implements ParseService {
    public List<FruitTransaction> parseTransactions(List<String> inputLines) {
        List<FruitTransaction> fruitList = new ArrayList<>();
        for (String line : inputLines) {
            line = line.trim();
            String[] transaction = line.split("\\s*,\\s*");
            if (transaction.length != 3) {
                throw new IllegalArgumentException("Invalid input: " + line);
            }
            fruitList.add(new FruitTransaction(FruitTransaction
                    .Operation.fromCode(transaction[0]),
                    transaction[1],
                    Integer.parseInt(transaction[2])));
        }
        return fruitList;
    }
}
