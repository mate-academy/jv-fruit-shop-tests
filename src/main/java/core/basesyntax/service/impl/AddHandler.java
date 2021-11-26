package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.strategy.OptionHandler;

public class AddHandler implements OptionHandler {
    private static final int DEFAULT_QUALITY = 0;
    private final FruitDao dao;

    public AddHandler(FruitDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(Transaction transaction) {
        Fruit fruit = new Fruit(transaction.getFruitName());
        int quantity = transaction.getQuantity();
        int oldQuantity = dao.getQuantity(fruit).orElse(DEFAULT_QUALITY);
        quantity += oldQuantity;
        dao.add(fruit,quantity);
    }
}
