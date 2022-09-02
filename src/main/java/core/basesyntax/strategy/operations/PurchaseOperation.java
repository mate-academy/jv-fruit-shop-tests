package core.basesyntax.strategy.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements DailyOperationHandler {
    private FruitDao fruitDao;

    public PurchaseOperation(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(FruitTransaction dailyTransaction) {
        if (dailyTransaction == null) {
            throw new RuntimeException("Daily transaction is null");
        }
        int amountFruits = dailyTransaction.getQuantity();
        int amountFruitsInStock = fruitDao.get(dailyTransaction.getFruitName()).getQuantity();
        if (amountFruitsInStock - amountFruits < 0) {
            throw new RuntimeException("Not enough fruit in stock, we have "
                    + amountFruitsInStock + ", but you sell " + amountFruits);
        }
        fruitDao.get(dailyTransaction.getFruitName())
                .setQuantity(amountFruitsInStock - amountFruits);
    }
}
