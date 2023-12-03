package core.basesyntax.impl.operation.operations;

import core.basesyntax.dao.AccountDao;
import core.basesyntax.dao.AccountDaoImpl;
import core.basesyntax.impl.operation.operations.exception.NegativeBalanceException;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandlerImpl implements OperationHandler {
    private final AccountDao accountDao = new AccountDaoImpl();

    @Override
    public void handleTransaction(FruitTransaction fruitTransaction) {
        Integer fruitQuantity = accountDao.getAmountByFruit(fruitTransaction.getFruit());
        Integer newFruitQuantity = fruitQuantity - fruitTransaction.getQuantity();
        if (newFruitQuantity < 0) {
            throw new NegativeBalanceException("Quantity can not be negative after purchase!");
        }
        accountDao.put(fruitTransaction.getFruit(), newFruitQuantity);
    }
}
