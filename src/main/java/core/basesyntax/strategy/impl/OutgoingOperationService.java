package core.basesyntax.strategy.impl;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.OperationService;

public class OutgoingOperationService implements OperationService {
    private final DaoStorage daoStorage;

    public OutgoingOperationService(DaoStorage daoStorage) {
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
        String fruit = fruitTransaction.getFruit();
        int newQuantity = daoStorage.getValue(fruit) - fruitTransaction.getQuantity();

        if (newQuantity < 0) {
            throw new RuntimeException("Incorrect operation, not enough product: " + fruit);
        }
        daoStorage.setNewValue(fruit, newQuantity);
    }
}
