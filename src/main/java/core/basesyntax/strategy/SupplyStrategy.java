package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public class SupplyStrategy extends FruitShopStrategy {

    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Can't supply less than 0 fruits");
        }
        storageDao.add(transaction.getFruit(), transaction.getQuantity());
    }
}
