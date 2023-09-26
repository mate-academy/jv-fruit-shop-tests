package core.basesyntax.service.impl;

import core.basesyntax.model.FruitDto;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationsHandler;
import java.util.List;

public class DataProcessingServiceImpl implements DataProcessingService {
    private final int indexOfOperationType;
    private final int indexOfFruitSort;
    private final int indexOfOperationsAmount;
    private final FruitService fruitService;

    public DataProcessingServiceImpl(FruitService fruitService, int indexOfOperationType,
                                     int indexOfFruitSort,
                                     int indexOfOperationsAmount) {
        this.fruitService = fruitService;
        this.indexOfOperationType = indexOfOperationType;
        this.indexOfFruitSort = indexOfFruitSort;
        this.indexOfOperationsAmount = indexOfOperationsAmount;
    }

    @Override
    public void processing(List<String[]> transactions) {
        OperationsHandler operationsHandler;
        for (String[] transaction : transactions) {
            operationsHandler = fruitService
                             .getOperationStrategies(transaction[indexOfOperationType]);
            operationsHandler.operation(new FruitDto(transaction[indexOfFruitSort],
                    Integer.parseInt(transaction[indexOfOperationsAmount])));
        }
    }
}
