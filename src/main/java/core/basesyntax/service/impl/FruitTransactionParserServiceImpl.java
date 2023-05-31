package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParserService;
import java.util.ArrayList;
import java.util.List;

public class FruitTransactionParserServiceImpl implements FruitTransactionParserService {
    private static final String SEPARATOR = ",";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int SKIP_TITLE_INDEX = 1;

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        lines.stream()
                .skip(SKIP_TITLE_INDEX)
                .map(i -> i.split(SEPARATOR))
                .forEach(rows -> fruitTransactions.add(new FruitTransaction(
                        FruitTransaction.Operation.getOperationType(rows[TYPE_INDEX]),
                        rows[FRUIT_INDEX], Integer.parseInt(rows[QUANTITY_INDEX]))));
        return fruitTransactions;
    }
}
