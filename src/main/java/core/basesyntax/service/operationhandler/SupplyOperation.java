package core.basesyntax.service.operationhandler;

import static core.basesyntax.db.ShopDataBase.shopData;

import core.basesyntax.service.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        if (shopData.get(fruitTransaction.getFruit()) == null) {
            shopData.put(fruitTransaction.getFruit(),fruitTransaction.getQuantity());
        } else {
            int oldQuantity = shopData.get(fruitTransaction.getFruit());
            shopData.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity() + oldQuantity);
        }
    }
}
