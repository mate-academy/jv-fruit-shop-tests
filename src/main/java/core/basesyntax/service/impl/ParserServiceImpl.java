package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ParserServiceImpl implements ParserService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int FRUIT_QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parse(List<String> data) {
        if (data == null) {
            throw new RuntimeException("Input param is null.");
        }
        if (data.isEmpty()) {
            throw new RuntimeException("Input param is empty.");
        }
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (String string : data) {
            String[] splittedData = string.split(",");
            try {
                Optional<FruitTransaction.Operation> operationName =
                        FruitTransaction.Operation.get(splittedData[OPERATION_INDEX]);
                if (splittedData[FRUIT_NAME_INDEX].isEmpty()) {
                    throw new NoSuchElementException();
                }
                FruitTransaction fruit = new FruitTransaction(operationName.get(),
                        splittedData[FRUIT_NAME_INDEX],
                        Integer.parseInt(splittedData[FRUIT_QUANTITY_INDEX]));
                fruitTransactions.add(fruit);
            } catch (NoSuchElementException | IndexOutOfBoundsException e) {
                throw new RuntimeException("Bad data in line: " + (fruitTransactions.size() + 1));
            }
        }
        return fruitTransactions;
    }
}
