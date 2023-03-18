package core.basesyntax.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DaoServiceHashMapTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static final Integer VALUE_POSITIVE = 100;
    private static final Integer VALUE_NEGATIVE = -100;
    private static final Integer VALUE_ZERO = 0;
    private static final Integer VALUE_NULL = null;
    private static final String ADDITIONAL_FRUIT = "banana";
    private static final DaoService<String, Integer> STORAGE
            = new DaoServiceHashMap();
    private static final String CONTAINS_VALUE_FOR = "Storage already contains value for ";
    private static final String CANNOT_BE_NEGATIVE = "Balance cannot be negative";
    private static final String CANNOT_BE_NULL = "Balance value cannot be null";

    @Before
    public void reset() {
        STORAGE.clear();
    }

    @Test
    public void create_withKeyExisting_notOk() {
        STORAGE.create(DEFAULT_FRUIT, VALUE_POSITIVE);
        try {
            STORAGE.create(DEFAULT_FRUIT, VALUE_POSITIVE);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals(e.getMessage(),
                    CONTAINS_VALUE_FOR + DEFAULT_FRUIT);
        }
    }

    @Test
    public void create_withValueNegative_notOk() {
        try {
            STORAGE.create(DEFAULT_FRUIT, VALUE_NEGATIVE);
            Assert.fail();
        } catch (NumberFormatException e) {
            Assert.assertEquals(e.getMessage(),
                    CANNOT_BE_NEGATIVE);
        }
    }

    @Test
    public void create_withValueNull_notOk() {
        try {
            STORAGE.create(DEFAULT_FRUIT, VALUE_NULL);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertEquals(e.getMessage(),
                    CANNOT_BE_NULL);
        }
    }

    @Test
    public void update_withValuePositive_ok() {
        STORAGE.update(DEFAULT_FRUIT, VALUE_POSITIVE);
    }

    @Test
    public void update_withValueNegative_notOk() {
        try {
            STORAGE.update(DEFAULT_FRUIT, VALUE_NEGATIVE);
            Assert.fail();
        } catch (NumberFormatException e) {
            Assert.assertEquals(e.getMessage(),
                    CANNOT_BE_NEGATIVE);
        }
    }

    @Test
    public void update_getByKey_ok() {
        STORAGE.create(DEFAULT_FRUIT, VALUE_POSITIVE);
        Assert.assertEquals(STORAGE.getByKey(DEFAULT_FRUIT), VALUE_POSITIVE);
    }

    @Test
    public void clear_withExistingElements_ok() {
        STORAGE.create(DEFAULT_FRUIT, VALUE_POSITIVE);
        STORAGE.clear();
        Assert.assertEquals(STORAGE.getAll(),
                new DaoServiceHashMap().getAll());
    }
}
