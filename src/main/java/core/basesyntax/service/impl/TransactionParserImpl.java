package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionParserImpl implements TransactionParser {
    private static final int INDEX_TYPE_OPERATION = 0;
    private static final int INDEX_FRUIT = 1;
    private static final int INDEX_QUANTITY = 2;

    @Override
    public List<FruitTransaction> parseFile(List<String> records) {
        for (String record : records) {
            if (record == null) {
                throw new RuntimeException("One or more records in DB is null");
            }
        }
        return records.stream()
                .skip(1)
                .map(line -> line.trim().split(","))
                .map(splitLine -> new FruitTransaction(splitLine[INDEX_TYPE_OPERATION],
                        splitLine[INDEX_FRUIT],
                        Integer.parseInt(splitLine[INDEX_QUANTITY])))
                .map(this::checkRecords)
                .collect(Collectors.toList());
    }

    private FruitTransaction checkRecords(FruitTransaction fruitTransaction) {
        List<String> listOfTypeOperations = Arrays.stream(FruitTransaction.Operation.values())
                .map(FruitTransaction.Operation::getOperations)
                .collect(Collectors.toList());

        if (!listOfTypeOperations.contains(fruitTransaction.getOperation())) {
            throw new RuntimeException("Unknown type operation");
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity must be greater then 0");
        }
        if (fruitTransaction.getFruit().isBlank()) {
            throw new RuntimeException("Fruit can't be empty");
        }
        return fruitTransaction;
    }
}

