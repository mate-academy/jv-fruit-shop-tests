package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;

public class SupplyOperationHandler implements OperationHandler {
    private static final int MIN_SUPPLY_QUANTITY = 1;

    @Override
    public void apply(FruitRecord transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        int supplyQuantity = transaction.getQuantity();
        if (supplyQuantity < 0) {
            throw new IllegalArgumentException("Supply quantity cannot be negative.");
        }
        Storage.storage.merge(transaction.getFruit(), supplyQuantity, Integer::sum);
    }
}
