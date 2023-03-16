package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CalculatorService;
import core.basesyntax.strategy.impl.StrategyStorageImpl;
import java.util.List;
import java.util.Objects;

public class CalculatorServiceImpl implements CalculatorService {
    private final StrategyStorageImpl strategy;

    public CalculatorServiceImpl(StrategyStorageImpl strategy) {
        this.strategy = strategy;
    }

    @Override
    public void calculate(List<FruitTransaction> order) {
        if (order == null) {
            throw new RuntimeException("Order can't be null");
        }
        if (order.isEmpty()) {
            throw new RuntimeException("Order can't be empty");
        }
        if (order.stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("Null element found in the order");
        }
        order.stream()
                .forEach(transaction -> strategy.getStrategy(transaction)
                        .handle(transaction));
    }
}
