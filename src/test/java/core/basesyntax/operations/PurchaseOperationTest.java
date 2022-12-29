package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.OperationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;
    
    @BeforeClass
    public static void beforeClass() {
        purchaseOperation = new PurchaseOperation();
    }

    @Before
    public void setUp() {
        fruitStorage.put("apple", 10);
    }

    @Test
    public void purchaseApples_Ok() {
        purchaseOperation.action("apple", 7);
        int actual = fruitStorage.get("apple");
        assertEquals(3, actual);
    }

    @Test(expected = OperationException.class)
    public void purchaseNullFruitName_NotOk() {
        purchaseOperation.action(null, 7);
    }

    @Test(expected = OperationException.class)
    public void purchaseWithNegativeAmount_NotOk() {
        purchaseOperation.action("apple", -1);
    }

    @Test(expected = OperationException.class)
    public void purchaseZeroFruits_NotOk() {
        purchaseOperation.action("apple", 0);
    }

    @Test(expected = OperationException.class)
    public void purchaseUnknownFruits_NotOk() {
        purchaseOperation.action("orange", 7);
    }

    @Test(expected = OperationException.class)
    public void purchaseOverAmount_NotOk() {
        purchaseOperation.action("apple", 11);
    }

    @After
    public void afterEach() {
        fruitStorage.clear();
    }
}
