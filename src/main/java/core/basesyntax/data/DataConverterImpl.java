package core.basesyntax.data;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int OPERATION = 0;
    private static final int FRUIT = 1;
    private static final int QUANTITY = 2;
    private static final int MIN_LINE_PARTS_LENGTH = 3;

    @Override
    public List<FruitTransaction> convertToTransactionsListFromLines(List<String> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Lines from file can't be null");
        }

        List<FruitTransaction> transactionList = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] lineParts = lines.get(i).split(",");
            isLineValid(lineParts);
            FruitTransaction fruitTransaction = new FruitTransaction();
            fruitTransaction.setFruit(lineParts[FRUIT]);
            fruitTransaction.setOperation(FruitTransaction.Operation
                    .fromCode(lineParts[OPERATION]));
            fruitTransaction.setQuantity(Integer.parseInt(lineParts[QUANTITY]));
            transactionList.add(fruitTransaction);
        }
        return transactionList;
    }

    private boolean isLineValid(String[] lineParts) {
        if (lineParts.length < MIN_LINE_PARTS_LENGTH) {
            throw new IllegalArgumentException("Line must have 3 arguments");
        }
        return true;
    }
}
