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

public class DecreaseAmountHandlerTest {
    private static Map<Fruit, Integer> storage;
    private static Fruit banana;
    private static Fruit apple;
    private static Fruit cherry;
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    private final OperationHandler decreaseAmountHandler = new DecreaseAmountHandler();

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
        decreaseAmountHandler.processData(banana, 10);
        decreaseAmountHandler.processData(apple, 20);
        decreaseAmountHandler.processData(cherry, 30);
        Map<Fruit, Integer> expectedStorageAfterProcessing
                = Map.of(banana, 10, apple, 10, cherry, 10);
        Map<Fruit, Integer> actualStorageAfterProcessing = Storage.getStorageOfFruits();
        assertNotNull(actualStorageAfterProcessing);
        assertEquals(expectedStorageAfterProcessing, actualStorageAfterProcessing);
    }

    @Test
    public void processData_nullFruit_notOk() {
        exceptionRule.expect(NullPointerException.class);
        decreaseAmountHandler.processData(null, 10);
    }

    @Test
    public void processData_wrongQuantity_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Quantity to subtract must be less than actual "
                + "quantity of fruits. The actual quantity of banana is: 20");
        decreaseAmountHandler.processData(banana, 99999);
    }

    @After
    public void afterEachTest() {
        storage.clear();
    }
}
