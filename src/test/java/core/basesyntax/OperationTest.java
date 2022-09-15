package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTest {
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String INVALID_OPERATION = "invalid code";
    private static Transaction.Operation supplyOperation;
    private static Transaction.Operation purchaseOperation;

    @BeforeClass
    public static void before() {
        supplyOperation = Transaction.Operation.SUPPLY;
        purchaseOperation = Transaction.Operation.PURCHASE;
    }

    @Test
    public void getCode_SupplyOperation_Ok() {
        String expectedOperationCode = "s";
        assertEquals(expectedOperationCode, supplyOperation.getCode());
    }

    @Test
    public void getCode_PurchaseOperationCode_Ok() {
        String expectedOperationCode = "p";
        assertEquals(expectedOperationCode, purchaseOperation.getCode());
    }

    @Test (expected = RuntimeException.class)
    public void getCode_InvalidOperationCode_Not_Ok() {
        String expectedOperationCode = "p";
        purchaseOperation = Transaction.Operation.valueOf(INVALID_OPERATION);
        assertEquals(expectedOperationCode, purchaseOperation.getCode());
    }

    @Test
    public void getByCode_SupplyOperation_Ok() {
        Transaction.Operation expectedOperation = Transaction.Operation.SUPPLY;
        assertEquals(expectedOperation, supplyOperation.getByCode(SUPPLY));
    }

    @Test
    public void getByCode_PurchaseOperation_Ok() {
        Transaction.Operation expectedOperation = Transaction.Operation.PURCHASE;
        assertEquals(expectedOperation, purchaseOperation.getByCode(PURCHASE));
    }
}
