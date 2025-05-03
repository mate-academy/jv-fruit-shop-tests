package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void before() {
        supplyOperationHandler = new SupplyOperationHandler();
        transaction = new FruitTransaction(Operation.SUPPLY, "apple", 150);
    }

    @Test
    public void validSupplyOperation_Ok() {
        Storage.fruits.put("apple", 100);
        Integer expected = Storage.fruits.get(transaction.getFruit()) + transaction.getQuantity();
        supplyOperationHandler.handle(transaction);
        Integer actual = Storage.fruits.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noValidSupplyOperation_NotOk() {
        Storage.fruits.put("apple", 100);
        Integer expected = Storage.fruits.get(transaction.getFruit()) / transaction.getQuantity();
        supplyOperationHandler.handle(transaction);
        Integer actual = Storage.fruits.get(transaction.getFruit());
        Assert.assertNotEquals(expected, actual);
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }
}
