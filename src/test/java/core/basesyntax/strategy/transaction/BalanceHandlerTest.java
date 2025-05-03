package core.basesyntax.strategy.transaction;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.TransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static TransactionHandler handler;
    private static Fruit fruit;
    private static String fruitType = "banana";
    private static int quantity = 20;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceHandler(new TransactionDaoImpl());
        fruit = new Fruit(fruitType, quantity);
    }

    @Test
    public void doTransaction_Ok() {
        handler.doTransaction(fruitType, quantity);
        assertTrue(Storage.fruits.contains(fruit));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
