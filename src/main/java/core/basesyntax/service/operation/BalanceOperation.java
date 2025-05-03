package core.basesyntax.service.operation;

import core.basesyntax.database.Storage;
import core.basesyntax.database.dao.FruitDao;
import core.basesyntax.database.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exception.InvalidDataException;

public class BalanceOperation implements OperationHandler {
    private final FruitDao fruitDao;

    public BalanceOperation() {
        this.fruitDao = new FruitDaoImpl();
    }

    @Override
    public void operate(FruitTransaction fruitTransaction) {
        if (Storage.STORAGE.containsKey(fruitTransaction.getName())) {
            throw new InvalidDataException("Balance for '" + fruitTransaction.getName()
                        + "' is already set");
        }
        fruitDao.add(fruitTransaction.getName(), fruitTransaction.getQuantity());
    }
}
