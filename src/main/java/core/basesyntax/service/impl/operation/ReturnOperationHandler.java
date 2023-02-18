package core.basesyntax.service.impl.operation;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    private WarehouseDao warehouseDao;

    public ReturnOperationHandler(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        if (warehouseDao.isPresent(fruit)) {
            int quantityFromDb = warehouseDao.getQuantity(transaction.getFruit());
            warehouseDao.setQuantity(fruit, quantityFromDb + transaction.getQuantity());
        } else {
            warehouseDao.setQuantity(fruit, transaction.getQuantity());
        }
    }
}

