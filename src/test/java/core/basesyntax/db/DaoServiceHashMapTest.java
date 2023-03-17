package core.basesyntax.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DaoServiceHashMapTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static final Integer VALUE_POSITIVE = 100;
    private static final Integer VALUE_NEGATIVE = -100;
    private static final Integer VALUE_ZERO = 0;
    private static final Integer VALUE_NULL = null;
    private static final String ADDITIONAL_FRUIT = "banana";
    private static final DaoService<String, Integer> STORAGE
            = new DaoServiceHashMap<String, Integer>();

    @BeforeEach
    public void reset() {
        STORAGE.clear();
    }

    @Test
    public void create_withKeyExisting_notOk() {
        STORAGE.create(DEFAULT_FRUIT, VALUE_POSITIVE);
        Assertions.assertThrows(RuntimeException.class,
                () -> STORAGE.create(DEFAULT_FRUIT, VALUE_POSITIVE));
    }

    @Test
    public void create_withValueNegative_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> STORAGE.create(DEFAULT_FRUIT, VALUE_NEGATIVE));
    }

    @Test
    public void create_withValueNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> STORAGE.create(DEFAULT_FRUIT, VALUE_NULL));
    }

    @Test
    public void update_withValueNegative_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> STORAGE.create(DEFAULT_FRUIT, VALUE_NEGATIVE));
    }

    @Test
    public void clear_withExistingElements_ok() {
        STORAGE.create(DEFAULT_FRUIT, VALUE_POSITIVE);
        STORAGE.clear();
        Assertions.assertEquals(STORAGE.getAll(),
                new DaoServiceHashMap<>().getAll());
    }
}
