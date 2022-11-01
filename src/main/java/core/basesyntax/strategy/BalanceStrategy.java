package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public class BalanceStrategy extends FruitShopStrategy {

    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Cant balance fruit "
                    + "with quantity less than 0");
        }
        storageDao.set(transaction.getFruit(), transaction.getQuantity());
    }
}
