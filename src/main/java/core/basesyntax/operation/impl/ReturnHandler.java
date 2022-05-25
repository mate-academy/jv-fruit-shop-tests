package core.basesyntax.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;

public class ReturnHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public ReturnHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void getHandler(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("FruitTransaction can not be null");
        }
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit can not be null");
        }
        if (!fruitTransaction.getFruit().chars().allMatch(Character::isLetter)) {
            throw new RuntimeException("Fruit name must contain letters only");
        }
        if (fruitTransaction.getOperation() == null) {
            throw new RuntimeException("Fruit operation can not be null");
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Fruit quantity can not be less than 0");
        }
        FruitTransaction fruitTransactionInDB = fruitDao.get(fruitTransaction.getFruit());
        if (fruitTransactionInDB == null) {
            fruitDao.add(fruitTransaction);
        } else {
            int total = fruitTransactionInDB.getQuantity() + fruitTransaction.getQuantity();
            fruitTransactionInDB.setQuantity(total);
            fruitDao.add(fruitTransactionInDB);
        }
    }
}
