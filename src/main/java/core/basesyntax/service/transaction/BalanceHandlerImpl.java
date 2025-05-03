package core.basesyntax.service.transaction;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandlerImpl implements TransactionHandler {
    private final FruitDao fruitDao;

    public BalanceHandlerImpl() {
        fruitDao = new FruitDaoImpl();
    }

    @Override
    public void handleTransaction(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        if (fruitDao.getAll().containsKey(fruit)) {
            throw new RuntimeException("Fruit" + fruit + "already exist in a storage");
        }
        fruitDao.add(fruit, transaction.getQuantity());
    }
}
