package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    private FruitDao fruitDao;

    public PurchaseOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getQuantity() > Storage.fruitsQuantity.get(transaction.getFruit())) {
            throw new RuntimeException("Needed quantity (" + transaction.getQuantity()
                    + ") is not available in the Storage.");
        }
        int negativeQuantity = - transaction.getQuantity();
        fruitDao.add(transaction.getFruit(), negativeQuantity);
    }
}
