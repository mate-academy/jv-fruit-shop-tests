package core.basesyntax.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    private final FruitDao dao = new FruitDaoImpl();

    @Override
    public void processOperation(FruitTransaction fruitTransaction) {
        dao.addFruit(fruitTransaction.getFruit(), fruitTransaction.getAmount());
    }
}
