package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static final Fruit mango = new Fruit("mango");
    private static final Integer INCREASE_BY_MANGO = 20;
    private static final Fruit cherry = new Fruit("cherry");
    private static final Integer CHERRY_QUANTITY = 150;
    private static final Fruit banana = new Fruit("banana");
    private static final Integer BANANA_QUANTITY = 50;
    private static final Fruit blueberry = new Fruit("blueberry");
    private static final Integer BLUEBERRY_QUANTITY = 23;
    private static final Integer INCREASE_BY_CHERRY = 45;
    private static final Integer INCREASE_BY_BANANA = 55;
    private static final Integer INCREASE_BY_BLUEBERRY = 425;
    private static Operation operation;

    @BeforeClass
    public static void beforeClass() {
        operation = new AddOperation();
    }

    @Before
    public void before() {
        Storage.getFruits().clear();
    }

    @Test
    public void addTest_Ok() {
        Storage.getFruits().put(cherry, CHERRY_QUANTITY);
        Storage.getFruits().put(banana, BANANA_QUANTITY);
        Storage.getFruits().put(blueberry, BLUEBERRY_QUANTITY);

        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("cherry"), 195);
        expected.put(new Fruit("banana"), 105);
        expected.put(new Fruit("blueberry"), 448);

        operation.apply(cherry, INCREASE_BY_CHERRY);
        operation.apply(banana, INCREASE_BY_BANANA);
        operation.apply(blueberry, INCREASE_BY_BLUEBERRY);
        Map<Fruit, Integer> actual = Storage.getFruits();

        assertEquals(expected, actual);
    }

    @Test
    public void addIntoTheEmptyStorageTest_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(mango, 20);
        operation.apply(mango, INCREASE_BY_MANGO);
        Map<Fruit, Integer> actual = Storage.getFruits();

        assertEquals(expected, actual);
    }
}

