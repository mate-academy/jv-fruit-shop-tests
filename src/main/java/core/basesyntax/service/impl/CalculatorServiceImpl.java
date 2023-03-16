package core.basesyntax.service.impl;

import core.basesyntax.model.StorageTransaction;
import core.basesyntax.service.CalculatorService;
import core.basesyntax.strategy.Strategy;
import java.util.List;

public class CalculatorServiceImpl implements CalculatorService {
    private final Strategy strategy;

    public CalculatorServiceImpl(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void calculate(List<StorageTransaction> transactions) {
        if (transactions.isEmpty()) {
            throw new RuntimeException("Transaction list can't be empty");
        }
        for (StorageTransaction transaction : transactions) {
            strategy.getHandler(transaction).handle(transaction);
        }
    }
}
