package core.basesyntax.strategy.operation;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    private FruitsDao fruitsDao;

    public SupplyOperation(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void transaction(FruitTransaction fruitTransaction) {
        FruitTransaction fruitBalance = fruitsDao.get(fruitTransaction);
        Integer quantity = fruitBalance.getQuantity() + fruitTransaction.getQuantity();

        fruitBalance.setQuantity(quantity);
    }
}
