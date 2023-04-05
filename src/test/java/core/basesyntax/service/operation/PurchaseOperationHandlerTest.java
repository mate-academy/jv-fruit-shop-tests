package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        FruitStorage.fruitStorage.put("banana", 20);
        FruitStorage.fruitStorage.put("apple", 100);
    }

    @Test
    public void handle_purchaseOperationNormalQuantity_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(12);
        purchaseOperationHandler.handle(fruitTransaction);
        int expected = 8;
        int actual = FruitStorage.fruitStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void handle_purchaseOperationMoreThanStorageQuantity_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(125);
        purchaseOperationHandler.handle(fruitTransaction);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
