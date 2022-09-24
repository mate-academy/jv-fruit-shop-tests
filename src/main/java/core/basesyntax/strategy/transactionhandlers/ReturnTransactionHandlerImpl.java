package core.basesyntax.strategy.transactionhandlers;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class ReturnTransactionHandlerImpl implements TransactionHandler {
    private FruitStorageDao dao = new FruitStorageDaoImpl();

    @Override
    public void transaction(FruitTransaction transaction) {
        int oldQuantity = dao.get(transaction.getName());
        dao.add(transaction.getName(), oldQuantity
                + transaction.getQuantity());
    }
}
