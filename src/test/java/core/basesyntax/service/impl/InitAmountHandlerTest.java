package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationHandler;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InitAmountHandlerTest {
    private static Map<Fruit, Integer> storage;
    private static Fruit banana;
    private static Fruit apple;
    private static Fruit cherry;
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    private final OperationHandler initAmountHandler = new InitAmountHandler();

    @BeforeClass
    public static void beforeClass() {
        storage = Storage.getStorageOfFruits();
        banana = new Fruit("banana");
        apple = new Fruit("apple");
        cherry = new Fruit("cherry");
    }

    @Test
    public void processData_validData_ok() {
        initAmountHandler.processData(banana, 20);
        initAmountHandler.processData(apple, 20);
        initAmountHandler.processData(cherry, 20);
        Map<Fruit, Integer> expectedStorageAfterProcessing
                = Map.of(banana, 20, apple, 20, cherry, 20);
        Map<Fruit, Integer> actualStorageAfterProcessing = Storage.getStorageOfFruits();
        assertNotNull(actualStorageAfterProcessing);
        assertEquals(expectedStorageAfterProcessing, actualStorageAfterProcessing);
    }

    @Test
    public void processData_nullFruit_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit can't be null!");
        initAmountHandler.processData(null, 10);
    }

    @Test
    public void processData_negativeQuantity_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Quantity of fruits to add must be positive."
                + " But not -10!");
        initAmountHandler.processData(banana, -10);
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }
}
