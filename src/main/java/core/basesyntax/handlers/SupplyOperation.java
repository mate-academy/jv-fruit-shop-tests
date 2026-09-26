package core.basesyntax.handlers;

import static core.basesyntax.handlers.AddFruitsToStorage.add;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.fruittransaction.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    private final FruitsDao dao;

    public SupplyOperation(FruitsDao dao) {
        this.dao = dao;
    }

    @Override
    public void updateData(FruitTransaction transaction) {
        add(transaction, dao);
    }
}
