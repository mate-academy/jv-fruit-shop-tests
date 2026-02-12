package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;

public class BalanceHandler implements OperationHandler {
    private final StorageDao storageDao;

    public BalanceHandler() {
        this.storageDao = new StorageDaoImpl();
    }

    @Override
    public void performOperation(FruitTransfer fruitTransfer) {
        if (fruitTransfer.getQuantity() < 0) {
            throw new InvalidDataException("It's not allowed negative amount. Amount is "
                    + fruitTransfer.getQuantity());
        }

        storageDao.addFruit(fruitTransfer.getFruit(), fruitTransfer.getQuantity());
    }
}
