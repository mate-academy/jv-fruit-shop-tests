package core.basesyntax.strategy.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements DailyOperationHandler {
    private FruitDao fruitDao;

    public BalanceOperation(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(FruitTransaction dailyTransaction) {
        if (dailyTransaction == null) {
            throw new RuntimeException("Daily transaction is null");
        }
        Fruit fruit = new Fruit();
        fruit.setFruitName(dailyTransaction.getFruitName());
        fruit.setQuantity(dailyTransaction.getQuantity());
        fruitDao.add(fruit);
    }
}
