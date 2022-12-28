package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.OperationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static final String APPLE_NAME = "apple";
    private static final String ORANGE_NAME = "orange";
    private static final String NULL_NAME = null;
    private static final int APPLE_BALANCE = 10;
    private static final int PURCHASE_AMOUNT = 7;
    private static final int OVER_AMOUNT = 11;
    private static final int REMAINDER_AMOUNT = 3;
    private static final int NEGATIVE_AMOUNT = -1;
    private static final int ZERO_AMOUNT = -1;
    private static PurchaseOperation purchaseOperation;
    
    @BeforeClass
    public static void beforeClass() {
        purchaseOperation = new PurchaseOperation();
    }

    @Before
    public void setUp() {
        fruitStorage.put(APPLE_NAME, APPLE_BALANCE);
    }

    @Test
    public void purchaseApples_Ok() {
        purchaseOperation.action(APPLE_NAME, PURCHASE_AMOUNT);
        int actual = fruitStorage.get(APPLE_NAME);
        assertEquals(REMAINDER_AMOUNT, actual);
    }

    @Test(expected = OperationException.class)
    public void purchaseNullFruitName_NotOk() {
        purchaseOperation.action(NULL_NAME, PURCHASE_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void purchaseWithNegativeAmount_NotOk() {
        purchaseOperation.action(APPLE_NAME, NEGATIVE_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void purchaseZeroFruits_NotOk() {
        purchaseOperation.action(APPLE_NAME, ZERO_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void purchaseUnknownFruits_NotOk() {
        purchaseOperation.action(ORANGE_NAME, PURCHASE_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void purchaseOverAmount_NotOk() {
        purchaseOperation.action(APPLE_NAME, OVER_AMOUNT);
    }

    @After
    public void afterEach() {
        fruitStorage.clear();
    }
}
