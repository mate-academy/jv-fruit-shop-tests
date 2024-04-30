package strategy.impl;

import strategy.FruitOperationStrategy;

public class PurchaseOperation implements FruitOperationStrategy {
    @Override
    public int execute(int currentValue, int value) {
        int newValue = currentValue - value;
        if (newValue < 0) {
            throw new IllegalArgumentException("We don't can Purchase "
                    + newValue + " less then 0");
        }
        return currentValue - value;
    }
}
