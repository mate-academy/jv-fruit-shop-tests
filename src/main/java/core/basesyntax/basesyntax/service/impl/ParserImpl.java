package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    private static final String SPLIT_SYMBOL = ",";
    private static final int INDEX_0 = 0;
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        List<FruitTransaction> result = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(SPLIT_SYMBOL);

            FruitTransaction.Operation operation =
                    FruitTransaction.Operation.fromCode(parts[INDEX_0]);
            String fruit = parts[INDEX_1];
            int quantity = Integer.parseInt(parts[INDEX_2]);

            result.add(new FruitTransaction(operation, fruit, quantity));
        }
        return result;
    }
}
