package core.basesyntax.strategy.handlers;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.model.GoodsOperation;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public PurchaseOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handleOperation(GoodsOperation operation) {
        checkValidItem(operation);
        String goodsName = operation.getItem();
        Integer itemStock = storageDao.getQuantityGoods(goodsName);
        int operationQuantity = operation.getQuantity();
        checkGoodsStock(itemStock, operationQuantity);
        storageDao.setQuantityGoods(goodsName, itemStock - operationQuantity);
    }

    private void checkValidItem(GoodsOperation operation) {
        if (operation.getItem() == null) {
            throw new RuntimeException("Goods name can't be null in transaction: " + operation);
        }
    }

    private void checkGoodsStock(int itemStock, int operationQuantity) {
        if (itemStock < operationQuantity) {
            throw new RuntimeException(
                    "Quantity of purchase operation can't be more than stock of goods");
        }
    }
}
