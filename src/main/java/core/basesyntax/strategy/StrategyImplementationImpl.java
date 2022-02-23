package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class StrategyImplementationImpl implements StrategyImplementation {
    private final HandlersStore serviceHandler;

    public StrategyImplementationImpl(HandlersStore handlersStore) {
        this.serviceHandler = handlersStore;
    }

    @Override
    public void strategy(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null) {
            throw new RuntimeException("Fruit transaction is empty");
        }
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            if (HandlersStore.getStrategy().get(fruitTransaction.getOperation()) == null) {
                throw new RuntimeException("Can't find operation");
            }
            HandlersStore.getStrategy()
                    .get(fruitTransaction
                            .getOperation()).handler(fruitTransaction);
        }
    }
}
