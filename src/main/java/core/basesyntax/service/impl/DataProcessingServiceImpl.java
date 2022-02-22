package core.basesyntax.service.impl;

import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.stream.IntStream;

public class DataProcessingServiceImpl implements DataProcessingService {
    private final OperationStrategy operationStrategy;

    public DataProcessingServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processingDate(List<FruitTransaction> data) {
        if (!data.isEmpty()) {
            IntStream.range(0, data.size()).forEach(i -> {
                OperationHandler operationService = operationStrategy
                        .applyOperation(data.get(i).getOperation());
                operationService.processOperation(data.get(i));
            });
        } else {
            throw new DataProcessingException("Can't processing empty report");
        }
    }
}
