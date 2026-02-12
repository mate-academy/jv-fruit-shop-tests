package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;

public class PurchaseHandler implements OperationHandler {
    private final StorageDao fruitDao;

    public PurchaseHandler() {
        this.fruitDao = new StorageDaoImpl();
    }

    @Override
    public void performOperation(FruitTransfer fruitTransfer) {
        if (fruitTransfer.getQuantity() < 0) {
            throw new InvalidDataException("It's not allowed negative amount. Amount is "
                    + fruitTransfer.getQuantity());
        }

        int storedFruits = fruitDao.getStoredQuantity(fruitTransfer.getFruit());
        if (fruitTransfer.getQuantity() > storedFruits) {
            throw new InvalidDataException("Invalid quantity, " + fruitTransfer.getFruit()
                    + " balance is "
                    + storedFruits);
        }
        int updatedFruitBalance = storedFruits - fruitTransfer.getQuantity();
        fruitDao.addFruit(fruitTransfer.getFruit(), updatedFruitBalance);
    }
}
