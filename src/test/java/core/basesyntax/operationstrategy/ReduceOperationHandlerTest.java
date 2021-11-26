package core.basesyntax.operationstrategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationFruitDto;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReduceOperationHandlerTest {
    private static OperationFruitDto reduceOperation;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        reduceOperation = new OperationFruitDto("r", "banana", 57);
        handler = new ReduceOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void apply_ReduceWithEnoughStorage_ok() {
        Storage.fruits.add(new Fruit("apple", 20));
        Storage.fruits.add(new Fruit("banana", 100));
        handler.apply(reduceOperation);
        Fruit expected = new Fruit("banana", 43);
        Fruit actual = null;
        for (Fruit fruit : Storage.fruits) {
            if (fruit.getName().equals(expected.getName())) {
                actual = fruit;
            }
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_ReduceNotEnoughStorage_ok() {
        Storage.fruits.add(new Fruit("apple", 20));
        Storage.fruits.add(new Fruit("banana", 20));
        handler.apply(reduceOperation);
    }

    @Test(expected = RuntimeException.class)
    public void apply_ReduceWithEmptyStorage_ok() {
        handler.apply(reduceOperation);
    }

    @Test(expected = RuntimeException.class)
    public void apply_ReduceNoSuchFruit_ok() {
        Storage.fruits.add(new Fruit("apple", 20));
        Storage.fruits.add(new Fruit("pineapple", 20));
        handler.apply(reduceOperation);
    }
}
