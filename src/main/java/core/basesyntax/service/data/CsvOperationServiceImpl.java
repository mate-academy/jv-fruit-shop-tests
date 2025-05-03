package core.basesyntax.service.data;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class CsvOperationServiceImpl implements OperationService<FruitTransaction> {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public CsvOperationServiceImpl(Map<FruitTransaction.Operation,
                                           OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public void processOperation(List<FruitTransaction> fruitTransactions) {
        fruitTransactions
                .forEach(o -> operationHandlerMap
                        .get(o.getOperation()).handle(o.getFruit(), o.getQuantity()));
    }
}
