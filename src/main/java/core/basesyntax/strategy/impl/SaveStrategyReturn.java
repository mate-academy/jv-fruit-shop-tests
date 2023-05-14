package core.basesyntax.strategy.impl;

import core.basesyntax.db.DaoService;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.SaveStrategy;

public class SaveStrategyReturn implements SaveStrategy<FruitTransaction> {
    private static final String NEGATIVE_STORAGE = "Cannot sell more fruit than exists in storage.";

    private final DaoService storage;

    public SaveStrategyReturn(DaoService storage) {
        this.storage = storage;
    }

    @Override
    public void save(FruitTransaction value) {
        if (value.getQuantity() == 0) {
            return;
        }
        if (!value.getOperation().equals(Operation.RETURN)) {
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
