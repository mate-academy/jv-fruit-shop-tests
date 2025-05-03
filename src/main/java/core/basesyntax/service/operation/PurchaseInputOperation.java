package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.model.FruitTransaction;

public class PurchaseInputOperation implements InputOperation {
    private final FruitTransactionDao fruitTransactionDao;

    public PurchaseInputOperation(FruitTransactionDao fruitTransactionDao) {
        this.fruitTransactionDao = fruitTransactionDao;
    }

    @Override
    public void process(FruitTransaction fruitOperation) {
        validate(fruitOperation);
        Integer quantity = fruitTransactionDao.get(fruitOperation);
        if (quantity >= fruitOperation.getQuantity()) {
            fruitOperation.setQuantity(quantity - fruitOperation.getQuantity());
            fruitTransactionDao.add(fruitOperation);
        }
    }
}
