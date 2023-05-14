package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public class StrategyImpl implements Strategy {
    private final Options options = new Options();

    @Override
    public FruitHandler choosePattern(FruitTransaction fruitTransaction) {
        options.initialHandler();
        return options.getHandlerMap().get(fruitTransaction.getOperation().trim());
    }
}
