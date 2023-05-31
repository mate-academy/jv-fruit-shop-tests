package core.basesyntax.service.strategy.handlers;

import core.basesyntax.dao.ActivityDaoDb;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public class PurchaseActivityHandlerImpl implements ActivityHandler {
    private final ActivityDaoDb activityDaoDb;

    public PurchaseActivityHandlerImpl(ActivityDaoDb activityDaoDb) {
        this.activityDaoDb = activityDaoDb;
    }

    @Override
    public void calculate(FruitTransaction transaction) {
        Fruit fruit = transaction.getFruit();
        int count = activityDaoDb.getCount(fruit);
        activityDaoDb.put(fruit, count - transaction.getCount());
    }
}
