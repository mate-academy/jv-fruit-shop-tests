package core.basesyntax.service.operation;

import core.basesyntax.database.dao.FruitDao;
import core.basesyntax.database.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exception.InvalidDataException;

public class PurchaseOperation implements OperationHandler {
    private final FruitDao fruitDao;

    public PurchaseOperation() {
        this.fruitDao = new FruitDaoImpl();
    }

    @Override
    public void operate(FruitTransaction fruitTransaction) {
        int quantityInStorage = fruitDao.get(fruitTransaction.getName());
        int transactionQuantity = fruitTransaction.getQuantity();
        if (quantityInStorage < transactionQuantity) {
            throw new InvalidDataException("Storage doesn't have required amount of '"
                    + fruitTransaction.getName() + "'");
        }
        fruitDao.add(fruitTransaction.getName(), quantityInStorage - transactionQuantity);
    }
}
