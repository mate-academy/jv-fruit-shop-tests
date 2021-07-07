package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.OperationType;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperationHandler();
    }

    @After
    public void after() {
        Storage.getFruits().clear();
    }

    @Test
    public void getBalance_Ok() {
        Transaction transaction = new Transaction(OperationType.BALANCE, "apple", 50);
        int expected = 50;
        int actual = new BalanceOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }
}
