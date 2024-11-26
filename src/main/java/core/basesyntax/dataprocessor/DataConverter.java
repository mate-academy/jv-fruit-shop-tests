package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String SEPARATOR = ",";

    public List<FruitTransaction> convert(List<String> rawData) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : rawData) {
            String[] parts = line.split(SEPARATOR);
            String type = parts[TYPE_INDEX];
            String fruit = parts[FRUIT_INDEX];
            int quantity = Integer.parseInt(parts[QUANTITY_INDEX]);

            transactions.add(new FruitTransaction(type, fruit, quantity));
        }
        return transactions;
    }
}
