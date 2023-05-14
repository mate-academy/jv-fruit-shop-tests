package core.basesyntax.strategy.impl;

import core.basesyntax.db.DaoService;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.SaveStrategy;

public class SaveStrategySupply implements SaveStrategy<FruitTransaction> {

    private final DaoService storage;

    public SaveStrategySupply(DaoService storage) {
        this.storage = storage;
    }

    @Override
    public void save(FruitTransaction value) {
        if (value.getQuantity() == 0) {
            return;
        }
        if (!value.getOperation().equals(Operation.SUPPLY)) {
            throw new RuntimeException(String.format(
                    WRONG_OPERATION,
                    value.getOperation().name(),
                    SaveStrategyBalance.class.getName()));
        }
        if (value.getQuantity() < 0) {
            throw new RuntimeException(NEGATIVE_TRANSACTION);
        }
        storage.update(value.getFruit(),
                (int) storage.getByKey(value.getFruit()) + value.getQuantity());
    }
}
