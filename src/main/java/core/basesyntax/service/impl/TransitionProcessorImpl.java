package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransitionProcessor;
import core.basesyntax.strategy.NegativeBalanceException;
import java.util.Map;

public class TransitionProcessorImpl implements TransitionProcessor {
    @Override
    public void processTransaction(Map<String, Integer> balanceMap, FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        if (transaction.getQuantity() < 0) {
            throw new NegativeBalanceException("Given transaction value is a negative number");
        }
        switch (transaction.getOperation()) {
            case BALANCE -> balanceMap.put(fruit, quantity);
            case SUPPLY, RETURN -> balanceMap.put(fruit,
                        balanceMap.getOrDefault(fruit, 0) + quantity);
            case PURCHASE -> balanceMap.put(fruit,
                        balanceMap.getOrDefault(fruit, 0) - quantity);
            default -> throw new NullPointerException("Invalid transaction type: "
                        + transaction.getOperation());
        }

        if (balanceMap.get(fruit) < 0) {
            throw new NegativeBalanceException("Balance for " + fruit
                    + " is below 0 after transaction.");
        }
    }
}
