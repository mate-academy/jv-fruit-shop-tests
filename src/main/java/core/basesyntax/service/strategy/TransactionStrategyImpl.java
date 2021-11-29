package core.basesyntax.service.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import java.util.List;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private Map<Operation, OperationHandler> operationHandlerMap;

    public TransactionStrategyImpl(Map<Operation, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public void execute(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            OperationHandler handler = operationHandlerMap.get(transaction.getOperation());
            handler.execute(transaction);
        }
    }
}
