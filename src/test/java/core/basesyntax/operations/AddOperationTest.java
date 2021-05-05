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
    private static final Fruit MANGO = new Fruit("mango");
    private static final Integer INCREASE_BY_MANGO = 20;
    private static final Fruit CHERRY = new Fruit("cherry");
    private static final Integer CHERRY_QUANTITY = 150;
    private static final Fruit BANANA = new Fruit("banana");
    private static final Integer BANANA_QUANTITY = 50;
    private static final Fruit BLUEBERRY = new Fruit("blueberry");
    private static final Integer BLUEBERRY_QUANTITY = 23;
    private static final Integer INCREASE_BY_CHERRY = 45;
    private static final Integer INCREASE_BY_BANANA = 55;
    private static final Integer INCREASE_BY_BLUEBERRY = 425;
    private static Operation operation;
    private Map<Fruit, Integer> expected;
    private Map<Fruit, Integer> actual;

    @BeforeClass
    public static void beforeClass() {
        operation = new AddOperation();
    }

    @Before
    public void before() {
        Storage.getFruits().clear();
        expected = new HashMap<>();
    }

    @Test
    public void addTest_Ok() {
        Storage.getFruits().put(CHERRY, CHERRY_QUANTITY);
        Storage.getFruits().put(BANANA, BANANA_QUANTITY);
        Storage.getFruits().put(BLUEBERRY, BLUEBERRY_QUANTITY);
        actual = Storage.getFruits();

        expected.put(CHERRY, 195);
        expected.put(BANANA, 105);
        expected.put(BLUEBERRY, 448);

        operation.apply(CHERRY, INCREASE_BY_CHERRY);
        operation.apply(BANANA, INCREASE_BY_BANANA);
        operation.apply(BLUEBERRY, INCREASE_BY_BLUEBERRY);

        assertEquals(expected, actual);
    }

    @Test
    public void addIntoTheEmptyStorageTest_Ok() {
        expected.put(MANGO, 20);
        operation.apply(MANGO, INCREASE_BY_MANGO);
        actual = Storage.getFruits();

        assertEquals(expected, actual);
    }
}

