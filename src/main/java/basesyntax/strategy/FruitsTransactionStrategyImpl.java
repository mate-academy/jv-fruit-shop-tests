package basesyntax.strategy;

import basesyntax.exceptions.TransactionException;
import java.util.Map;

public class FruitsTransactionStrategyImpl implements FruitsTransactionStrategy {
    private final Map<String, FruitHandler> fruitHandlerMap;

    public FruitsTransactionStrategyImpl(Map<String, FruitHandler> fruitHandlerMap) {
        this.fruitHandlerMap = fruitHandlerMap;
    }

    @Override
    public FruitHandler fruitHandler(String transactionType) {
        if (transactionType == null) {
            throw new TransactionException("The transaction type is equal to null");
        }
        return fruitHandlerMap.get(transactionType);
    }
}
