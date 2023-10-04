package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationSupplyTest {
    private static Storage storage = new FruitStorage();
    private static OperationSupply operationSupply;

    @BeforeAll
    public static void setUp() {
        storage.addFruitInQuantity("banana",20);
        storage.addFruitInQuantity("apple", 15);
        storage.addFruitInQuantity("melon",3);
        operationSupply = new OperationSupply(storage);
    }

    @Test
    public void purchaseOperation_Ok() {
        FruitTransaction banana = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 45);
        FruitTransaction apple = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 20);
        FruitTransaction melon = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "melon", 3);
        operationSupply.handleOperation(banana);
        operationSupply.handleOperation(apple);
        operationSupply.handleOperation(melon);
        Assert.assertEquals(35, storage.getQuantity("apple"));
        Assert.assertEquals(6, storage.getQuantity("melon"));
        Assert.assertEquals(65, storage.getQuantity("banana"));
    }
}
