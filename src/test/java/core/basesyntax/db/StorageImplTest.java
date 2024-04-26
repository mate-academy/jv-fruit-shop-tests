package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageImplTest {
    private static final String APPLE_FRUIT = "apple";
    private static final int APPLE_TEST_AMOUNT = 80;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new StorageImpl();
    }

    @Test
    public void setGetValue_Test() {
        storage.setValue(APPLE_FRUIT, APPLE_TEST_AMOUNT);

        int expected = APPLE_TEST_AMOUNT;
        int actual = storage.getValue(APPLE_FRUIT);

        assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        new StorageImpl().clear();
    }
}
