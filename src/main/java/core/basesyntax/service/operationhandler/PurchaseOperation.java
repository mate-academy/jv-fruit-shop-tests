package core.basesyntax.service.operationhandler;

import static core.basesyntax.db.ShopDataBase.shopData;

import core.basesyntax.service.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        if (shopData.get(fruitTransaction.getFruit()) == null) {
            throw new IllegalArgumentException("No such fruit in the shop: "
                    + fruitTransaction.getFruit());
        }
        int oldQuantity = shopData.get(fruitTransaction.getFruit());
        int newQuantity = oldQuantity - fruitTransaction.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("Not enough fruits in the storage, fruits in the shop : "
                    + oldQuantity
                    + ", required amount: "
                    + newQuantity);
        }
        shopData.put(fruitTransaction.getFruit(), newQuantity);
    }
}
