package core.basesyntax.testclasses;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.OperationService;

public class OperationServiceForTest implements OperationService {
    private final DaoStorage daoStorageForTest = new DaoStorageForTest();

    @Override
    public void calculation(FruitTransaction fruitTransaction) {
        daoStorageForTest.setNewValue(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
