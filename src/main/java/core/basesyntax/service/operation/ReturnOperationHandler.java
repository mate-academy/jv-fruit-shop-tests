package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void operation(FruitTransaction transaction, FruitDao dao) {
        dao.add(transaction.getFruit(), transaction.getQuantity());
    }
}
