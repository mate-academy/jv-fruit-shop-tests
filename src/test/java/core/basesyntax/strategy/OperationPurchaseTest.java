package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationPurchaseTest {
    private static Storage storage = new FruitStorage();
    private static OperationPurchase operationPurchase;

    @BeforeAll
    public static void setUp() {
        storage.addFruitInQuantity("banana",20);
        storage.addFruitInQuantity("apple", 40);
        storage.addFruitInQuantity("melon",10);
        operationPurchase = new OperationPurchase(storage);
    }

    @Test
    public void purchaseOperation_Ok() {
        FruitTransaction banana = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);
        FruitTransaction apple = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        FruitTransaction melon = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "melon", 3);
        operationPurchase.handleOperation(banana);
        operationPurchase.handleOperation(apple);
        operationPurchase.handleOperation(melon);
        Assert.assertEquals(20, storage.getQuantity("apple"));
        Assert.assertEquals(7, storage.getQuantity("melon"));
        Assert.assertEquals(15, storage.getQuantity("banana"));
    }

    @Test
    public void purchaseOperation_notEnoughtFruit_NotOk() {
        FruitTransaction banana = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 25);
        Assert.assertThrows(RuntimeException.class,
                () -> operationPurchase.handleOperation(banana));
    }

    @AfterAll
    public static void clear() {
        storage.removeAllItems();
    }

}
