package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationBalanceTest {

    private static Storage storage = new FruitStorage();
    private static OperationStrategy operationBalance;

    @BeforeAll
    public static void setUp() {
        operationBalance = new OperationBalance(storage);
    }

    @Test
    public void operationBalance_objectNull_NotOk() {
        Assert.assertThrows(NullPointerException.class,
                () -> operationBalance.handleOperation(null));
    }

    @Test
    public void checkBalance_Ok() {
        FruitTransaction banana = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);
        FruitTransaction apple = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);
        FruitTransaction melon = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "melon", 10);
        operationBalance.handleOperation(apple);
        operationBalance.handleOperation(melon);
        operationBalance.handleOperation(banana);
        operationBalance.handleOperation(melon);
        operationBalance.handleOperation(apple);
        Assert.assertEquals(100, storage.getQuantity("apple"));
        Assert.assertEquals(20, storage.getQuantity("melon"));
        Assert.assertEquals(100, storage.getQuantity("banana"));
    }
}
