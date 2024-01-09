package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.model.FruitTransaction;

public class SupplyInputOperation implements InputOperation {
    private final FruitTransactionDao fruitTransactionDao;

    public SupplyInputOperation(FruitTransactionDao fruitTransactionDao) {
        this.fruitTransactionDao = fruitTransactionDao;
    }

    @Override
    public void process(FruitTransaction fruitOperation) {
        validate(fruitOperation);
        Integer quantity = fruitTransactionDao.get(fruitOperation);
        fruitOperation.setQuantity(quantity + fruitOperation.getQuantity());
        fruitTransactionDao.add(fruitOperation);
    }
}
