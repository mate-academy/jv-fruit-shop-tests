package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler handler;
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        handler = new AddOperationHandler();
        Storage.getFruits().put(new Fruit("apple"), 10);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }

    @Test
    public void testSupply_Ok() {
        transaction = new Transaction(OperationType.SUPPLY, "apple", 50);
        int expected = 70;
        int actual = new AddOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testNegativeSupply_NotOk() {
        transaction = new Transaction(OperationType.SUPPLY, "apple", -5);
        new AddOperationHandler().apply(transaction);
    }

    @Test
    public void testReturn_Ok() {
        transaction = new Transaction(OperationType.SUPPLY, "apple", 10);
        int expected = 20;
        int actual = new AddOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }
}
