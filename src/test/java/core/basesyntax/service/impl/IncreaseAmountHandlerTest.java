package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationHandler;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IncreaseAmountHandlerTest {
    private static Map<Fruit, Integer> storage;
    private static Fruit banana;
    private static Fruit apple;
    private static Fruit cherry;
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    private final OperationHandler increaseAmountHandler = new IncreaseAmountHandler();

    @BeforeClass
    public static void beforeClass() {
        storage = Storage.getStorageOfFruits();
        banana = new Fruit("banana");
        apple = new Fruit("apple");
        cherry = new Fruit("cherry");
    }

    @Before
    public void setUp() {
        storage.put(banana, 20);
        storage.put(apple, 30);
        storage.put(cherry, 40);
    }

    @Test
    public void processData_validData_ok() {
        increaseAmountHandler.processData(banana, 80);
        increaseAmountHandler.processData(apple, 70);
        increaseAmountHandler.processData(cherry, 60);
        Map<Fruit, Integer> expectedStorageAfterProcessing
                = Map.of(banana, 100, apple, 100, cherry, 100);
        Map<Fruit, Integer> actualStorageAfterProcessing = Storage.getStorageOfFruits();
        assertNotNull(actualStorageAfterProcessing);
        assertEquals(expectedStorageAfterProcessing, actualStorageAfterProcessing);
    }

    @Test
    public void processData_nullFruit_notOk() {
        exceptionRule.expect(NullPointerException.class);
        increaseAmountHandler.processData(null, 10);
    }

    @Test
    public void processData_negativeQuantity_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Quantity of fruits to add must be positive."
                + " But not -10!");
        increaseAmountHandler.processData(banana, -10);
    }

    @After
    public void afterEachTest() {
        storage.clear();
    }
}
