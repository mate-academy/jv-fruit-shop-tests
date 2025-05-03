package core.basesyntax.strategy.handlers;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.model.GoodsOperation;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public ReturnOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handleOperation(GoodsOperation operation) {
        checkValidItem(operation);
        String goodsName = operation.getItem();
        int newQuantity = operation.getQuantity();
        Integer itemStock = storageDao.getQuantityGoods(goodsName);
        if (itemStock != null) {
            newQuantity += itemStock;
        }
        storageDao.setQuantityGoods(goodsName, newQuantity);
    }

    private void checkValidItem(GoodsOperation operation) {
        if (operation.getItem() == null) {
            throw new RuntimeException("Goods name can't be null in transaction: " + operation);
        }
    }
}
