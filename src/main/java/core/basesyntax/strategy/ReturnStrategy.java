package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exception.InvalidQuantityException;

public class ReturnStrategy extends FruitShopStrategy {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new InvalidQuantityException("Cant return less than 0 fruits");
        }
        storageDao.add(transaction.getFruit(), transaction.getQuantity());
    }
}
