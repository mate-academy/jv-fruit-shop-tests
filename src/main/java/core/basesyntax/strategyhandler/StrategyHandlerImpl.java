package core.basesyntax.strategyhandler;

import core.basesyntax.storage.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class StrategyHandlerImpl implements StrategyHandler {
    @Override
    public void strategyHandler(Map<FruitTransaction.Operation, OperationHandler> strategyMap,
                                List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruits : fruitTransactions) {
            if (fruits.getOperation() == null) {
                throw new IllegalArgumentException("Operation cant be NULL");
            }
            strategyMap.get(fruits.getOperation()).handleTransaction(fruits);
        }
    }
}
