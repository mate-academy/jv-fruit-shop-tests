package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.strategy.OptionHandler;

public class PurchaseHandler implements OptionHandler {
    private static final int DEFAULT_QUALITY = 0;
    private final FruitDao dao;

    public PurchaseHandler(FruitDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(Transaction transaction) {
        Fruit fruit = new Fruit(transaction.getFruitName());
        int quantity = transaction.getQuantity();
        int oldQuantity = dao.getQuantity(fruit).orElse(DEFAULT_QUALITY);
        if (oldQuantity < quantity) {
            throw new RuntimeException("We don't have that much fruit");
        }
        quantity = oldQuantity - quantity;
        dao.add(fruit,quantity);
    }
}
