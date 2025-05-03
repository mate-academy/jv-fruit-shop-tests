package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Converter;
import java.util.ArrayList;
import java.util.List;

public class ConverterImpl implements Converter {
    public static final String elementSeparator = ",";
    public static final int entrySize = 3;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> transactionData) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        if (transactionData == null || transactionData.isEmpty()) {
            throw new IllegalArgumentException("Transaction list cannot be null or empty");
        }
        for (String transactionEntry : transactionData) {
            String[] elements = transactionEntry.split(elementSeparator);
            if (elements.length != entrySize) {
                throw new IllegalArgumentException("Transaction contains invalid elements");
            }
            FruitTransaction fruitTransaction = new FruitTransaction();
            fruitTransaction.setOperation(FruitTransaction.Operation.fromCode(elements[0]));
            fruitTransaction.setFruitName(elements[1]);
            fruitTransaction.setQuantity(Integer.parseInt(elements[2]));
            fruitTransactions.add(fruitTransaction);
        }
        return fruitTransactions;
    }
}
