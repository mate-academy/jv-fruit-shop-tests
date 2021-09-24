package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitDataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit banana;
    private static Fruit apple;
    private int actual;
    private int expected;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceHandler();
        banana = new Fruit("banana");
        apple = new Fruit("apple");
    }

    @Test
    public void apply_ok() {
        FruitDataBase.storage.put(banana, 200);
        FruitDataBase.storage.put(apple, 200);
        expected = 200;
        actual = operationHandler.apply(new FruitRecordDto("b", banana, 200));
        assertEquals(expected, actual);
        expected = 200;
        actual = operationHandler.apply(new FruitRecordDto("b", apple, 200));
        assertEquals(expected, actual);
    }

    @Test
    public void apply_checkZero_ok() {
        FruitDataBase.storage.put(banana, 200);
        FruitDataBase.storage.put(apple, 200);
        expected = 0;
        actual = operationHandler.apply(new FruitRecordDto("r", banana, 0));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        FruitDataBase.storage.clear();
    }
}
