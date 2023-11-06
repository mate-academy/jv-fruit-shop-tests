package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    private static final String REGEX = ",";
    private static final int OPERATION = 0;
    private static final int FRUIT = 1;
    private static final int QUANTITY = 2;

    @Override
    public List<FruitTransaction> parseListToTransactionList(List<String> fileData) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        for (String line : fileData) {
            FruitTransaction fruitTransaction = new FruitTransaction();
            String[] data = line.split(REGEX);
            checkSize(data);
            String fruitName = data[FRUIT];
            final int quantity = Integer.parseInt(data[QUANTITY]);
            fruitTransaction.setOperation(Operation.getOperationByString(data[OPERATION]));
            checkFruitName(fruitName);
            fruitTransaction.setFruit(fruitName);
            checkQuantity(quantity);
            fruitTransaction.setQuantity(quantity);
            fruitTransactionList.add(fruitTransaction);
        }
        return fruitTransactionList;
    }

    private void checkQuantity(int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative" + quantity);
        }
    }

    private void checkFruitName(String name) {
        boolean containsLetters = name.matches(".*[a-zA-Z].*");
        if (!containsLetters) {
            throw new RuntimeException("Fruit name must have letters " + name);
        }
    }

    private void checkSize(String[] data) {
        if (data.length != 3) {
            throw new RuntimeException("Data must have length of 3 " + data.length);
        }
    }
}
