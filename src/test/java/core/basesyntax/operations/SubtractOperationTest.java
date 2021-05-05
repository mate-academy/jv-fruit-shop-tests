package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractOperationTest {
    private static final Fruit CHERRY = new Fruit("cherry");
    private static final Integer CHERRY_QUANTITY = 150;
    private static final Fruit BANANA = new Fruit("banana");
    private static final Integer BANANA_QUANTITY = 50;
    private static final Fruit BLUEBERRY = new Fruit("blueberry");
    private static final Integer BLUEBERRY_QUANTITY = 23;
    private static final Integer DECREASE_BY_CHERRY = 67;
    private static final Integer DECREASE_BY_BANANA = 50;
    private static final Integer DECREASE_BY_BLUEBERRY = 20;
    private static final Integer DECREASE_BIGGER_THAN_ACTUAL_CHERRY = 500;
    private static Operation operation;

    @BeforeClass
    public static void beforeClass() {
        Storage.getFruits().put(CHERRY, CHERRY_QUANTITY);
        Storage.getFruits().put(BANANA, BANANA_QUANTITY);
        Storage.getFruits().put(BLUEBERRY, BLUEBERRY_QUANTITY);
        operation = new SubtractOperation();
    }

    @Test
    public void subtractTest_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("cherry"), 83);
        expected.put(new Fruit("banana"), 0);
        expected.put(new Fruit("blueberry"), 3);

        operation.apply(CHERRY, DECREASE_BY_CHERRY);
        operation.apply(BANANA, DECREASE_BY_BANANA);
        operation.apply(BLUEBERRY, DECREASE_BY_BLUEBERRY);
        Map<Fruit, Integer> actual = Storage.getFruits();

        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void subtractValueBiggerThanActualTest_NotOk() {
        operation.apply(CHERRY, DECREASE_BIGGER_THAN_ACTUAL_CHERRY);
    }
}
