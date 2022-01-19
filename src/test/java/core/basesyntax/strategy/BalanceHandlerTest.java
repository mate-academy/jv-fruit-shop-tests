package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.model.TransactionType;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final TransactionHandler balanceHandler = new BalanceHandler();
    private static final Transaction regularTransaction = new Transaction(TransactionType.BALANCE,
            "fruit", 100);
    private static final Transaction zeroTransaction = new Transaction(TransactionType.BALANCE,
            "fruit", 0);
    private static final Transaction negativTransaction = new Transaction(TransactionType.BALANCE,
            "fruit", -100);
    private static final Transaction maxTransaction = new Transaction(TransactionType.BALANCE,
            "fruit", Integer.MAX_VALUE);
    private static final Transaction minTransaction = new Transaction(TransactionType.BALANCE,
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
        expected = regularTransaction.getAmount();
        for (int amount : amounts) {
            actual = balanceHandler.perform(amount, regularTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                    + regularTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test
    public void perform_zeroTransaction_Ok() {
        int expected;
        int actual;
        expected = zeroTransaction.getAmount();
        for (int amount : amounts) {
            actual = balanceHandler.perform(amount, zeroTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + zeroTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test
    public void perform_negativTransaction_Ok() {
        int expected;
        int actual;
        expected = negativTransaction.getAmount();
        for (int amount : amounts) {
            actual = balanceHandler.perform(amount, negativTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + negativTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test
    public void perform_maxTransaction_Ok() {
        int expected;
        int actual;
        expected = maxTransaction.getAmount();
        for (int amount : amounts) {
            actual = balanceHandler.perform(amount, maxTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + maxTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test
    public void perform_minTransaction_Ok() {
        int expected;
        int actual;
        expected = minTransaction.getAmount();
        for (int amount : amounts) {
            actual = balanceHandler.perform(amount, minTransaction);
            assertEquals("amount = " + amount + ", transaction.amount = "
                            + minTransaction.getAmount(),
                    expected, actual);
        }
    }

    @Test(expected = NullPointerException.class)
    public void perform_NullTransaction_NotOk() {
        balanceHandler.perform(regularAmount, null);
    }
}