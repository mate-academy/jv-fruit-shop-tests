package core.basesyntax.service.operation;

import core.basesyntax.database.dao.FruitDao;
import core.basesyntax.database.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    private FruitDao fruitDao;

    public SupplyOperation() {
        this.fruitDao = new FruitDaoImpl();
    }

    @Override
    public void operate(FruitTransaction fruitTransaction) {
        int quantityInStorage = fruitDao.get(fruitTransaction.getName());
        int transactionQuantity = fruitTransaction.getQuantity();
        fruitDao.add(fruitTransaction.getName(), quantityInStorage + transactionQuantity);
    }
}
