package core.basesyntax.strategy.transaction;

import core.basesyntax.dao.TransactionDao;

public class PurchaseHandler implements TransactionHandler {
    private final TransactionDao transactionDao;

    public PurchaseHandler(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public void doTransaction(String fruitType, int quantity) {
        int oldQuantity = transactionDao.getFruit(fruitType).getQuantity();
        if (oldQuantity < quantity) {
            throw new RuntimeException("You can't buy more fruits than there are in the shop");
        }
        transactionDao.getFruit(fruitType).setQuantity(oldQuantity - quantity);
    }
}
