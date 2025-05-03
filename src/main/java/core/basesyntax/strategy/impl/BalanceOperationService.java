package core.basesyntax.strategy.impl;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.OperationService;

public class BalanceOperationService implements OperationService {
    private final DaoStorage daoStorage;

    public BalanceOperationService(DaoStorage daoStorage) {
        if (daoStorage == null) {
            throw new IllegalArgumentException("The argument DaoStorage is NULL");
        }
        this.daoStorage = daoStorage;
    }

    @Override
    public void calculation(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new IllegalArgumentException("The argument DaoStorage is NULL");
        }
        daoStorage.setNewValue(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
