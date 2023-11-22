package core.basesyntax.impl.operation.operations;

import core.basesyntax.dao.AccountDao;
import core.basesyntax.dao.AccountDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandlerImpl implements OperationHandler {
    private final AccountDao accountDao = new AccountDaoImpl();

    @Override
    public void handleTransaction(FruitTransaction fruitTransaction) {
        accountDao.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
