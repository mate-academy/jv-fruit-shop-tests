package core.basesyntax.service;

import core.basesyntax.operations.TransactionExecutor;
import java.util.List;
import java.util.Map;

public class TransactionInvocationImpl implements TransactionInvocation {
    @Override
    public void invokeTransaction(List<FruitTransaction> fruitTransactionList,
                                  Map<FruitTransaction.Operation,
                                          TransactionExecutor> operationsMap) {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationsMap);

        for (FruitTransaction fruitTransaction : fruitTransactionList) {
            operationStrategy.get(fruitTransaction.getOperation())
                    .execute(fruitTransaction);
        }
    }
}
