package core.basesyntax.strategy.handler;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import java.util.NoSuchElementException;

public class ReturnOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public ReturnOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void process(FruitTransaction transaction) {
        Integer currentQuantity = fruitDao.get(transaction.getFruit());
        int purchaseQuantity = transaction.getQuantity();
        if (purchaseQuantity < 0) {
            throw new IllegalArgumentException("Invalid return quantity - " + purchaseQuantity);
        }

        if (!fruitDao.getStorage().containsKey(transaction.getFruit())) {
            throw new NoSuchElementException("Invalid return fruit - " + transaction.getFruit());
        }
        fruitDao.update(transaction.getFruit(), currentQuantity + purchaseQuantity);
    }
}
