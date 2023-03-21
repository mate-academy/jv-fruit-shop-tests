package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DaoServiceHashMapTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static final Integer VALUE_POSITIVE = 100;
    private static final Integer VALUE_NEGATIVE = -100;
    private static final Integer VALUE_ZERO = 0;
    private static final Integer VALUE_NULL = null;
    private static final String ADDITIONAL_FRUIT = "banana";
    private static DaoServiceHashMap storage;
    private static Map<String, Integer> expectedResult;
    private static final String CONTAINS_VALUE_FOR
            = "Storage already contains value for ";
    private static final String CANNOT_BE_NEGATIVE
            = "Balance cannot be negative";
    private static final String CANNOT_BE_NULL
            = "Balance value cannot be null";

    @BeforeClass
    public static void initialize() {
        storage = new DaoServiceHashMap();
        expectedResult = new HashMap<>();
    }

    @Before
    public void reset() {
        storage.getMemory().clear();
        expectedResult.clear();
    }

    @Test(expected = RuntimeException.class)
    public void create_withKeyExisting_notOk() {
        storage.create(DEFAULT_FRUIT, VALUE_POSITIVE);
        storage.create(DEFAULT_FRUIT, VALUE_POSITIVE);
    }

    @Test(expected = NumberFormatException.class)
    public void create_withValueNegative_notOk() {
        storage.create(DEFAULT_FRUIT, VALUE_NEGATIVE);
    }

    @Test(expected = NullPointerException.class)
    public void create_withValueNull_notOk() {
        storage.create(DEFAULT_FRUIT, VALUE_NULL);
    }

    @Test
    public void update_withValuePositive_ok() {
        expectedResult.put(DEFAULT_FRUIT, VALUE_POSITIVE);
        storage.getMemory().put(DEFAULT_FRUIT, VALUE_ZERO);
        storage.update(DEFAULT_FRUIT, VALUE_POSITIVE);
        Assert.assertEquals(storage.getMemory(), expectedResult);
    }

    @Test(expected = NumberFormatException.class)
    public void update_withValueNegative_notOk() {
        storage.update(DEFAULT_FRUIT, VALUE_NEGATIVE);
    }

    @Test
    public void getByKey_valid_ok() {
        storage.getMemory().put(DEFAULT_FRUIT, VALUE_POSITIVE);
        Assert.assertEquals(storage.getByKey(DEFAULT_FRUIT), VALUE_POSITIVE);
    }

    @Test
    public void clear_withExistingElements_ok() {
        storage.getMemory().put(DEFAULT_FRUIT, VALUE_POSITIVE);
        storage.clear();
        Assert.assertTrue(storage.getMemory().isEmpty());
    }
}
