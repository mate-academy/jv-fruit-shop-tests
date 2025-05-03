package core.basesyntax.service.transaction;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandlerImpl implements TransactionHandler {
    private final FruitDao fruitDao;

    public ReturnHandlerImpl() {
        fruitDao = new FruitDaoImpl();
    }

    @Override
    public void handleTransaction(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        if (!fruitDao.getAll().containsKey(fruit)) {
            fruitDao.add(fruit, quantity);
            return;
        }
        int oldQuantity = fruitDao.getQuantity(fruit);
        int newQuantity = oldQuantity + quantity;
        fruitDao.add(transaction.getFruit(), newQuantity);
    }
}
