package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitDaoTest {
    private static final String PINEAPPLE = "pineapple";
    private static final String COCONUT = "coconut";
    private static FruitDao dao;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitDaoImpl();
    }

    @Test
    void addFruit_validData_ok() {
        assertDoesNotThrow(() -> dao.addFruit(PINEAPPLE, 14));
    }

    @Test
    void getBalance_validData_ok() {
        Storage.getStock().put("coconut", 20);
        Map<String, Integer> expected = Map.of(COCONUT, 20);
        Map<String, Integer> actual = dao.getBalance();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.getStock().clear();
    }
}
