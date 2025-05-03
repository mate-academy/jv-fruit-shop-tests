package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileParseService;
import java.util.List;
import java.util.stream.Collectors;

public class FileParseServiceImpl implements FileParseService {
    private static final String WRONG_OPERATION = "Wrong operation!";
    private static final String SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    public List<FruitTransaction> parseDataFromCV(List<String> dataFromFile) {
        return dataFromFile.stream()
                .skip(1)
                .map(line -> {
                    if (line.isEmpty()) {
                        throw new IllegalArgumentException("Empty line found!");
                    }
                    String[] parts = line.split(SEPARATOR);
                    String operationCode = parts[OPERATION_INDEX];
                    try {
                        FruitTransaction.Operation operation = FruitTransaction
                                .fromCode(operationCode);
                        String fruit = parts[FRUIT_INDEX];
                        int quantity = Integer.parseInt(parts[QUANTITY_INDEX]);
                        return new FruitTransaction(operation, fruit, quantity);
                    } catch (RuntimeException e) {
                        throw new RuntimeException(WRONG_OPERATION, e);
                    }
                })
                .collect(Collectors.toList());
    }
}

