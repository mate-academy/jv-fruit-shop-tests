package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionTest {
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static Transaction.Operation supplyOperation;
    private static Transaction.Operation purchaseOperation;
    private static Transaction supplyTransaction;
    private static Transaction purchaseTransaction;

    @BeforeClass
    public static void before() {
        supplyOperation = Transaction.Operation.getByCode(SUPPLY);
        purchaseOperation = Transaction.Operation.getByCode(PURCHASE);
    }

    @Test
    public void getOperation_Supply_Ok() {
        supplyTransaction = new Transaction(supplyOperation, APPLE, 10);
        Transaction.Operation expectedOperation = Transaction.Operation.getByCode(SUPPLY);
        assertEquals(expectedOperation, supplyTransaction.getOperation());
    }

    @Test
    public void getOperation_Purchase_Ok() {
        purchaseTransaction = new Transaction(purchaseOperation, BANANA, 200);
        Transaction.Operation expected = Transaction.Operation.getByCode(PURCHASE);
        assertEquals(expected, purchaseTransaction.getOperation());
    }

    @Test
    public void getProductName_supplyTransaction_Ok() {
        supplyTransaction = new Transaction(supplyOperation, APPLE, 10);
        String expected = "apple";
        assertEquals(expected, supplyTransaction.getProductName());
    }

    @Test
    public void getQuantity_purchaseTransaction_Ok() {
        purchaseTransaction = new Transaction(purchaseOperation, BANANA, 200);
        int expected = 200;
        assertEquals(expected, purchaseTransaction.getQuantity());
    }
}
