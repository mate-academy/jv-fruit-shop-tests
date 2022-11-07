package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.QuantityService;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class QuantityServiceImplTest {
    private static final Map<Fruit, Integer> ACTUAL = Storage.fruits;
    private static QuantityService quantityService;
    private static Map<Fruit, Integer> expected;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        quantityService = new QuantityServiceImpl();
        expected = new HashMap<>();
        fruit = new Fruit("banana");
    }

    @Before
    public void setUp() {
        expected.put(fruit, 20);
    }

    @Test
    public void add_ok() {
        quantityService.add(fruit, 20);
        assertEquals(expected, ACTUAL);
    }

    @Test(expected = RuntimeException.class)
    public void add_fruitIsNull_notOk() {
        quantityService.add(null, 20);
    }

    @Test(expected = RuntimeException.class)
    public void add_quantityIsNull_notOk() {
        quantityService.add(fruit, null);
    }

    @Test
    public void subtract_ok() {
        quantityService.add(fruit, 30);
        quantityService.subtract(fruit, 10);
        assertEquals(expected, ACTUAL);
    }

    @Test(expected = NoSuchElementException.class)
    public void subtract_fruitIsNull_notOk() {
        quantityService.subtract(new Fruit("apple"), 10);
    }

    @Test(expected = RuntimeException.class)
    public void subtract_quantityIsNull_notOk() {
        quantityService.add(fruit, 30);
        quantityService.subtract(fruit, null);
    }

    @After
    public void tearDown() {
        ACTUAL.clear();
    }
}
