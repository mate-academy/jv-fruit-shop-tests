package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationReturnTest {
    private static Storage storage = new FruitStorage();
    private static OperationReturn operationReturn;

    @BeforeAll
    public static void setUp() {
        storage.addFruitInQuantity("banana",20);
        storage.addFruitInQuantity("apple", 40);
        storage.addFruitInQuantity("melon",10);
        operationReturn = new OperationReturn(storage);
    }

    @Test
    public void purchaseOperation_Ok() {
        FruitTransaction banana = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 5);
        FruitTransaction apple = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 20);
        FruitTransaction melon = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "melon", 3);
        operationReturn.handleOperation(banana);
        operationReturn.handleOperation(apple);
        operationReturn.handleOperation(melon);
        Assert.assertEquals(60, storage.getQuantity("apple"));
        Assert.assertEquals(13, storage.getQuantity("melon"));
        Assert.assertEquals(25, storage.getQuantity("banana"));
    }
}
