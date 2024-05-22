package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.exception.PurchasingException;
import core.basesyntax.model.FruitTransaction;

public class PurchaseStrategyHandlerImpl implements StrategyHandler {
    private final FruitDao fruitDao;

    public PurchaseStrategyHandlerImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        int quantity = fruitTransaction.getQuantity();
        if (quantity > fruitDao.getFruitMap().get(fruitName)) {
            throw new PurchasingException("There are not enough fruits in the store.");
        }
        Integer diffQuantity = fruitTransaction.getQuantity() * (-1);
        fruitDao.getFruitMap().merge(fruitName, diffQuantity, Integer::sum);
    }
}
