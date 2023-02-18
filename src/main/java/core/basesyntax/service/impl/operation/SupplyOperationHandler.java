package core.basesyntax.service.impl.operation;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    private WarehouseDao warehouseDao;

    public SupplyOperationHandler(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Fruit quantity can't be negative");
        }
        if (warehouseDao.isPresent(fruit)) {
            int quantityFromDb = warehouseDao.getQuantity(transaction.getFruit());
            warehouseDao.setQuantity(fruit, quantityFromDb + transaction.getQuantity());
        } else {
            warehouseDao.setQuantity(fruit, transaction.getQuantity());
        }
    }
}
