package core.basesyntax.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public SupplyOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (!Storage.fruits.containsKey(fruitTransaction.getFruit())) {
            fruitDao.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        } else {
            fruitDao.addition(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        }
    }
}
