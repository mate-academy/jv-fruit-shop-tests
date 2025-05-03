package core.basesyntax.impl.operation.operations;

import core.basesyntax.dao.AccountDao;
import core.basesyntax.dao.AccountDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandlerImpl implements OperationHandler {
    private final AccountDao accountDao = new AccountDaoImpl();

    @Override
    public void handleTransaction(FruitTransaction fruitTransaction) {
        Integer fruitQuantity = accountDao.getAmountByFruit(fruitTransaction.getFruit());
        Integer newFruitQuantity = fruitQuantity + fruitTransaction.getQuantity();
        accountDao.put(fruitTransaction.getFruit(), newFruitQuantity);
    }
}
