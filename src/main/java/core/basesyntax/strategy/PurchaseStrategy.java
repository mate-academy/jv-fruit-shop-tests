package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exception.InvalidQuantityException;
import core.basesyntax.service.exception.PurchasingException;

public class PurchaseStrategy extends FruitShopStrategy {

    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new InvalidQuantityException("Can't purchase less than 0 fruits");
        }
        int currentQuantity = storageDao.get(transaction.getFruit())
                .orElseThrow(()
                        -> new RuntimeException("Theres no such fruit: "
                        + transaction.getFruit()));
        if (transaction.getQuantity() > currentQuantity) {
            throw new PurchasingException("You can't remove from storage more than it have (have "
                    + currentQuantity + ")");
        }
        storageDao.subtract(transaction.getFruit(), transaction.getQuantity());
    }
}
