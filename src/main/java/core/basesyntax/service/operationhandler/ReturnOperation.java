package core.basesyntax.service.operationhandler;

import static core.basesyntax.db.ShopDataBase.shopData;

import core.basesyntax.service.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        if (shopData.get(fruitTransaction.getFruit()) == null) {
            throw new IllegalArgumentException("No such fruit in the shop: "
                    + fruitTransaction.getFruit());
        }
        int oldQuantity = shopData.get(fruitTransaction.getFruit());
        shopData.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity() + oldQuantity);
    }
}
