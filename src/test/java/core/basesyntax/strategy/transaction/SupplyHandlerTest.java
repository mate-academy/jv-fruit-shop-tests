package core.basesyntax.strategy.transaction;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.TransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static TransactionHandler handler;
    private static Fruit fruit;
    private static Fruit expectedFruit;
    private static String fruitType = "banana";
    private static int quantity = 20;
    private static int returnedFruits = 5;

    @BeforeClass
    public static void beforeClass() {
        handler = new SupplyHandler(new TransactionDaoImpl());
        fruit = new Fruit(fruitType, quantity);
        expectedFruit = new Fruit(fruitType, quantity + returnedFruits);
        Storage.fruits.add(fruit);
    }

    @Test
    public void doTransaction_Ok() {
        handler.doTransaction(fruitType, returnedFruits);
        Fruit actual = Storage.fruits.stream()
                .filter(f -> f.getFruitType().equals(fruitType))
                .findFirst()
                .get();
        assertEquals(actual, expectedFruit);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
