package core.basesyntax.service.strategy;

import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {

    private Map<String, TransactionHandler> transactionHandlerMap;

    public TransactionStrategyImpl(
            Map<String, TransactionHandler> transactionHandlerMap) {
        this.transactionHandlerMap = transactionHandlerMap;
    }

    @Override
  public TransactionHandler get(String operation) {
        if (transactionHandlerMap.containsKey(operation)) {
            return transactionHandlerMap.get(operation);
        }
        throw new RuntimeException("Wrong operation index received");
    }
}
