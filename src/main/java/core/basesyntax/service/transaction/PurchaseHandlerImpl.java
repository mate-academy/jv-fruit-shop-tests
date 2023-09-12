package core.basesyntax.service.transaction;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandlerImpl implements TransactionHandler {
    private final FruitDao fruitDao;

    public PurchaseHandlerImpl() {
        fruitDao = new FruitDaoImpl();
    }

    @Override
    public void handleTransaction(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        if (!fruitDao.getAll().containsKey(fruit)) {
            throw new RuntimeException("There is no fruit " + fruit + " available");
        }
        int oldQuantity = fruitDao.getQuantity(transaction.getFruit());
        int quantity = transaction.getQuantity();
        if (oldQuantity < quantity) {
            throw new RuntimeException("Not enough " + fruit + " at the store for buying");
        }
        int newQuantity = oldQuantity - quantity;
        fruitDao.add(transaction.getFruit(), newQuantity);
    }
}
