package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private final Fruit apple = new Fruit("apple");
    private final OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    void purchaseProductByPurchaseHandler_NotOk() {
        Storage.storage.put(apple,20);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        Assert.assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.apply(fruitTransaction));
    }

    @Test
    void purchaseProductByPurchaseHandler_Ok() {
        Storage.storage.put(apple,40);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        purchaseOperationHandler.apply(fruitTransaction);
        Integer expected = 7;
        Integer actual = Storage.storage.get(apple);
        Assertions.assertEquals(expected, actual);
    }
}
