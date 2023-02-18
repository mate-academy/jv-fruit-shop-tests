package core.basesyntax.service.impl.operation;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    private WarehouseDao warehouseDao;

    public BalanceOperationHandler(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getQuantity() >= 0) {
            warehouseDao.setQuantity(transaction.getFruit(), transaction.getQuantity());
        } else {
            throw new RuntimeException("Fruit quantity for balance сan't be negative");
        }
    }
}
