package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exception.InvalidQuantityException;

public class SupplyStrategy extends FruitShopStrategy {

    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new InvalidQuantityException("Can't supply less than 0 fruits");
        }
        storageDao.add(transaction.getFruit(), transaction.getQuantity());
    }
}
