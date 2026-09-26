package core.basesyntax.handlers;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.fruittransaction.FruitTransaction;

import static core.basesyntax.handlers.AddFruitsToStorage.add;

public class BalanceOperation implements OperationHandler {
    private final FruitsDao dao;

    public BalanceOperation(FruitsDao dao) {
        this.dao = dao;
    }

    @Override
    public void updateData(FruitTransaction transaction) {
        add(transaction, dao);
    }
}
