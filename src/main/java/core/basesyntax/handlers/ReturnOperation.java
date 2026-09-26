package core.basesyntax.handlers;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.fruittransaction.FruitTransaction;

import static core.basesyntax.handlers.AddFruitsToStorage.add;

public class ReturnOperation implements OperationHandler {
    private final FruitsDao dao;

    public ReturnOperation(FruitsDao dao) {
        this.dao = dao;
    }

    @Override
    public void updateData(FruitTransaction transaction) {
        add(transaction, dao);
    }


}
