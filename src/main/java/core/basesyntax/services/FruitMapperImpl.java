package core.basesyntax.services;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;

public class FruitMapperImpl implements FruitMapper {
    private static final String COMMA = ",";
    private static final String INVALID_FORMAT = "Invalid data format: ";
    private static final String EMPTY_INPUT_DATA = "The list cannot be empty: ";
    private static final String NULL_INPUT_DATA = "The list cannot be null: ";

    @Override
    public List<FruitTransaction> mapData(List<String> data) {
        checkOnNullEmpty(data);
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (String line : data) {
            String[] splitLine = line.split(COMMA);
            if (splitLine.length != 3) {
                throw new IllegalArgumentException(INVALID_FORMAT + line);
            }
            String operationCode = splitLine[0];
            String fruit = splitLine[1];
            int quantity = Integer.parseInt(splitLine[2]);
            Operation operation = Operation.getOperationByCode(operationCode);
            fruitTransactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return fruitTransactions;
    }

    private void checkOnNullEmpty(List<String> data) {
        if (data == null) {
            throw new IllegalArgumentException(NULL_INPUT_DATA + data);
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_INPUT_DATA + data);
        }
    }
}
