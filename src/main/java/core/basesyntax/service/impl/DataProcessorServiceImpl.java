package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataProcessorService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.List;

public class DataProcessorServiceImpl implements DataProcessorService {
    private static final String DELIMITER = ",";
    private static final int INDEX_OF_HEADER = 0;
    private static final int INDEX_OF_OPERATION = 0;
    private static final int INDEX_OF_FRUIT = 1;
    private static final int INDEX_OF_QUANTITY = 2;
    private OperationStrategy operationStrategy;

    public DataProcessorServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void updateDataInStorage(List<String> readData) {
        readData.remove(INDEX_OF_HEADER);
        for (String line : readData) {
            FruitTransaction transaction = new FruitTransaction();
            String[] separatedData = line.split(DELIMITER);
            transaction.setFruit(separatedData[INDEX_OF_FRUIT]);
            transaction.setQuantity(checkIndex(separatedData[INDEX_OF_QUANTITY]));
            updateNumberOfFruitInTransaction(separatedData[INDEX_OF_OPERATION], transaction);
        }
    }

    private void updateNumberOfFruitInTransaction(
            String code, FruitTransaction transaction) {
        OperationHandler handlerForTransaction = operationStrategy
                .getOperationHandler(Operation.getOperation(code));
        handlerForTransaction.updateNumberOfFruit(transaction);
    }

    private int checkIndex(String inputIndex) {
        int index;
        try {
            index = Integer.parseInt(inputIndex);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Quantity isn't represented with numbers, " + inputIndex);
        }
        if (index < 0) {
            throw new RuntimeException("Quantity is less than 0, " + inputIndex);
        }
        return index;
    }
}
