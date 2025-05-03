package core.basesyntax.service.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class ReturnService implements OperationHandler {
    private final FruitDao fruitDao;

    public ReturnService(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (!fruitDao.getStorageData().containsKey(fruitTransaction.getFruitName())) {
            throw new RuntimeException(
                    "Impossible transaction. Unknown fruit: " + fruitTransaction.getFruitName());
        }
        int beginAmount = fruitDao.getAmount(fruitTransaction.getFruitName());
        int newAmount;
        newAmount = beginAmount + fruitTransaction.getQuantity();
        fruitDao.changeAmount(fruitTransaction.getFruitName(), newAmount);
    }
}
