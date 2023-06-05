package core.basesyntax.service.impl;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.Strategy;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final Strategy strategy;

    public FruitServiceImpl(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        checkListOfTransactions(fruitTransactions);
        for (FruitTransaction fruit: fruitTransactions) {
            OperationHandler handler = strategy.get(fruit.getOperation());
            handler.handle(fruit);
        }
    }

    private void checkListOfTransactions(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions.equals(null) || fruitTransactions.size() == 0) {
            throw new NullPointerException("Cant process empty list of fruit transactions");
        }
    }
}
