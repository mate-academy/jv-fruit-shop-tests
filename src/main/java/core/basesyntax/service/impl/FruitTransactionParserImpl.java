package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FruitTransactionParserImpl implements FruitTransactionParser {
    private static final int HEADER_LINE = 1;
    private static final String CSV_SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parse(List<String> data) {
        return data.stream()
                .skip(HEADER_LINE)
                .map(this::parseLineToFruitTransaction)
                .collect(Collectors.toList());
    }

    private FruitTransaction parseLineToFruitTransaction(String line) {
        String[] splittedLine = line.split(CSV_SEPARATOR);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(getOperationByValue(splittedLine[OPERATION_INDEX]));
        fruitTransaction.setFruit(new Fruit(splittedLine[FRUIT_NAME_INDEX]));
        fruitTransaction.setQuantity(Integer.parseInt(splittedLine[QUANTITY_INDEX]));
        return fruitTransaction;
    }

    private FruitTransaction.Operation getOperationByValue(String value) {
        return Arrays.stream(FruitTransaction.Operation.values())
                .filter(o -> o.getOperation().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unsupported operation: " + value));
    }
}
