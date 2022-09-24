package core.basesyntax.strategy.transactionhandlers;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class BalanceTransactionHandlerImpl implements TransactionHandler {
    private FruitStorageDao dao = new FruitStorageDaoImpl();

    @Override
    public void transaction(FruitTransaction transaction) {
        dao.add(transaction.getName(), transaction.getQuantity());
    }
}
