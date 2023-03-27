package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;

public class ParseServiceImpl implements ParseService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> getFruitTransactions(List<String> list) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (String line : list) {
            String[] splitter = line.split(",");
            fruitTransactions.add(new FruitTransaction(splitter[OPERATION_INDEX],
                    splitter[FRUIT_INDEX], Integer.parseInt(splitter[QUANTITY_INDEX])));
        }
        return fruitTransactions;
    }
}
