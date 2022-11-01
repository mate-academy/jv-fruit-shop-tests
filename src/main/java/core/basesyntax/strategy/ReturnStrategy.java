package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public class ReturnStrategy extends FruitShopStrategy {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Cant return less than 0 fruits");
        }
        storageDao.add(transaction.getFruit(), transaction.getQuantity());
    }
}
