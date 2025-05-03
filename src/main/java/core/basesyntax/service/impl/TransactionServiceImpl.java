package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TransactionServiceImpl implements TransactionService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String SIGN_TO_SPLIT = ",";

    @Override
    public List<FruitTransaction> processData(List<String> rawDataFromFile) {
        if (rawDataFromFile == null) {
            throw new RuntimeException("Data from the file can`t be null");
        } else if (rawDataFromFile.size() == 0) {
            throw new RuntimeException("Data from the file should contain at least 1 string");
        }
        return IntStream.range(1, rawDataFromFile.size())
                .mapToObj(i -> processLine(rawDataFromFile.get(i)))
                .collect(Collectors.toList());
    }

    private FruitTransaction processLine(String line) {
        if (line.isEmpty()) {
            throw new RuntimeException("File contains invalid data");
        }
        String[] dataArr = line.split(SIGN_TO_SPLIT);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.getOperationType(dataArr[OPERATION_INDEX]));
        fruitTransaction.setFruitType(dataArr[FRUIT_TYPE_INDEX]);
        if (Integer.parseInt(dataArr[AMOUNT_INDEX]) < 0) {
            throw new RuntimeException("Fruit amount can`t be negative");
        }
        fruitTransaction.setAmount(Math.abs(Integer.parseInt(dataArr[AMOUNT_INDEX])));
        return fruitTransaction;
    }
}
