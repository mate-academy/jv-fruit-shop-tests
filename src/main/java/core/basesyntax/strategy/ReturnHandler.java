package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;

public class ReturnHandler implements OperationHandler {
    private final StorageDao fruitDao;

    public ReturnHandler() {
        this.fruitDao = new StorageDaoImpl();
    }

    @Override
    public void performOperation(FruitTransfer fruitTransfer) {
        if (fruitTransfer.getQuantity() < 0) {
            throw new InvalidDataException("It's not allowed negative amount. Amount is "
                    + fruitTransfer.getQuantity());
        }

        int storedFruits = fruitDao.getStoredQuantity(fruitTransfer.getFruit());
        int updatedFruitBalance = storedFruits + fruitTransfer.getQuantity();
        fruitDao.addFruit(fruitTransfer.getFruit(), updatedFruitBalance);
    }
}
