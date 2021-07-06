package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperationHandler();
        Storage.getFruits().put(new Fruit("apple"), 30);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }

    @Test
    public void getBalance_Ok() {
        transaction = new Transaction(OperationType.BALANCE, "apple", 50);
        int expected = 50;
        int actual = new BalanceOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }
}
