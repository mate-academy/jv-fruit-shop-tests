package core.basesyntax.strategy.impl;

import core.basesyntax.dao.impl.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsHandler;

public record SupplyOperationHandler(FruitDao fruitDao) implements OperationsHandler {
    @Override
    public void applyOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new RuntimeException(
                "Supply quantity has incorrect value: " + fruitTransaction.getQuantity());
        }
        int currentQuantity = fruitDao.getQualityByItemType(fruitTransaction.getFruit());
        int newQuantity = currentQuantity + fruitTransaction.getQuantity();
        fruitDao.putToStorage(fruitTransaction.getFruit(), newQuantity);
    }
}
