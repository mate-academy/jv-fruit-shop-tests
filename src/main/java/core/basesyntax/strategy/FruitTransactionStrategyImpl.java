package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class FruitTransactionStrategyImpl implements FruitTransactionStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> fruitTransactionMap;

    public FruitTransactionStrategyImpl(Map<FruitTransaction.Operation, OperationHandler>
                                                fruitTransactionMap) {
        this.fruitTransactionMap = fruitTransactionMap;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Input operation is null");
        }
        return fruitTransactionMap.get(operation);
    }
}
