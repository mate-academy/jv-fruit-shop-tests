package core.basesyntax.services.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.DataProcessorService;
import java.util.List;
import java.util.stream.Stream;
import javax.management.openmbean.InvalidOpenTypeException;

public class DataProcessorServiceImpl implements DataProcessorService {
    private static final String SPLIT_DELIMITER = ",";
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int FRUIT_QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> processInputData(List<String> dataFromFile) {
        return dataFromFile.stream()
                .map(this::getFruitTransaction)
                .toList();
    }

    private FruitTransaction getFruitTransaction(String data) {
        String[] processedData = data.split(SPLIT_DELIMITER);
        String operationType = processedData[OPERATION_TYPE_INDEX];
        FruitTransaction.Operation operation = FruitTransaction.Operation
                .getOperationByCode(operationType);
        String fruitType = processedData[FRUIT_TYPE_INDEX];
        int fruitQuantity = Integer.parseInt(processedData[FRUIT_QUANTITY_INDEX]);
        if (fruitQuantity < 0) {
            throw new InvalidDataException("Invalid Quantity, fruit quantity is: "
                    + fruitQuantity);
        }
        if (Stream.of("b", "s", "p", "r").anyMatch(s -> !operationType.equals(s))) {
            throw new InvalidOpenTypeException("Invalid operation type");
        }
        return new FruitTransaction(operation, fruitType, fruitQuantity);
    }
}
