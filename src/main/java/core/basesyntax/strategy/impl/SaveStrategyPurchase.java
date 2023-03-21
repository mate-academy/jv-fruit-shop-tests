package core.basesyntax.strategy.impl;

import core.basesyntax.db.DaoService;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.SaveStrategy;

public class SaveStrategyPurchase implements SaveStrategy<FruitTransaction> {

    private final DaoService storage;

    public SaveStrategyPurchase(DaoService storage) {
        this.storage = storage;
    }

    @Override
    public void save(FruitTransaction value) {
        if (value.getQuantity() == 0) {
            return;
        }
        if (!value.getOperation().equals(Operation.PURCHASE)) {
            throw new RuntimeException(String.format(
                    WRONG_OPERATION,
                    value.getOperation().name(),
                    SaveStrategyBalance.class.getName()));
        }
        if (value.getQuantity() < 0) {
            throw new RuntimeException(NEGATIVE_TRANSACTION);
        }
        final Integer existingValue = (Integer) storage.getByKey(value.getFruit());
        if (existingValue == null || existingValue - value.getQuantity() < 0) {
            throw new RuntimeException(NEGATIVE_STORAGE);
        }
        storage.update(value.getFruit(),
                (int) storage.getByKey(value.getFruit()) - value.getQuantity());
    }
}
