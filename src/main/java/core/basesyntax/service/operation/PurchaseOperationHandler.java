package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void operation(FruitTransaction transaction, FruitDao dao) {
        dao.remove(transaction.getFruit(), transaction.getQuantity());
    }
}
