package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.model.TransactionType;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final TransactionHandler purchaseHandler = new PurchaseHandler();
    private static final Transaction regularTransaction = new Transaction(TransactionType.PURCHASE,
            "fruit", 100);
    private static final Transaction zeroTransaction = new Transaction(TransactionType.PURCHASE,
            "fruit", 0);
    private static final Transaction negativTransaction = new Transaction(TransactionType.PURCHASE,
            "fruit", -100);
    private static final Transaction maxTransaction = new Transaction(TransactionType.PURCHASE,
            "fruit", Integer.MAX_VALUE);
    private static final Transaction minTransaction = new Transaction(TransactionType.PURCHASE,
            "fruit", Integer.MIN_VALUE);
    private static final int regularAmount = 100;
    private static final int zeroAmount = 0;
    private static final int negativAmount = -100;
    private static final int minAmount = Integer.MIN_VALUE;
    private static final int maxAmount = Integer.MAX_VALUE;
    private static final int[] amounts = {regularAmount, zeroAmount, negativAmount,
            minAmount, maxAmount};

    @Test
    public void perform_regularTransaction_Ok() {
        int expected;
        int actual;
        for (int amount : amounts) {
            expected = amount - regularTransaction.getAmount();
            actual = purchaseHandler.perform(amount, regularTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + regularTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test
    public void perform_zeroTransaction_Ok() {
        int expected;
        int actual;
        for (int amount : amounts) {
            expected = amount - zeroTransaction.getAmount();
            actual = purchaseHandler.perform(amount, zeroTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + zeroTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test
    public void perform_negativTransaction_Ok() {
        int expected;
        int actual;
        for (int amount : amounts) {
            expected = amount - negativTransaction.getAmount();
            actual = purchaseHandler.perform(amount, negativTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + negativTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test
    public void perform_maxTransaction_Ok() {
        int expected;
        int actual;
        for (int amount : amounts) {
            expected = amount - maxTransaction.getAmount();
            actual = purchaseHandler.perform(amount, maxTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + maxTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test
    public void perform_minTransaction_Ok() {
        int expected;
        int actual;
        for (int amount : amounts) {
            expected = amount - minTransaction.getAmount();
            actual = purchaseHandler.perform(amount, minTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + minTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test(expected = NullPointerException.class)
    public void perform_NullTransaction_NotOk() {
        purchaseHandler.perform(regularAmount, null);
    }
}