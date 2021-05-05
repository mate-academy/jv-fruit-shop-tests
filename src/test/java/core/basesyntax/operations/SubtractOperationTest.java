package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
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
    private static FruitDao fruitDao;
    private static Operation operation;
    private Map<Fruit, Integer> expected;
    private Map<Fruit, Integer> actual;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        fruitDao.update(CHERRY, CHERRY_QUANTITY);
        fruitDao.update(BANANA, BANANA_QUANTITY);
        fruitDao.update(BLUEBERRY, BLUEBERRY_QUANTITY);
        operation = new SubtractOperation();
    }

    @Before
    public void before() {
        expected = new HashMap<>();
    }

    @Test
    public void subtractTest_Ok() {
        expected.put(CHERRY, 83);
        expected.put(BANANA, 0);
        expected.put(BLUEBERRY, 3);

        operation.apply(CHERRY, DECREASE_BY_CHERRY);
        operation.apply(BANANA, DECREASE_BY_BANANA);
        operation.apply(BLUEBERRY, DECREASE_BY_BLUEBERRY);
        actual = Storage.getFruits();

        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void subtractValueBiggerThanActualTest_NotOk() {
        operation.apply(CHERRY, DECREASE_BIGGER_THAN_ACTUAL_CHERRY);
    }
}
