package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitDaoImplTest {
    private static final String FRUIT = "banana";
    private static final int FRUIT_QUANTITY = 20;
    private static final int STORED_FRUIT_QUANTITY = 20;
    private static FruitDaoImpl fruitDao;

    @BeforeAll
    static void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    void add_validInput_Ok() {
        fruitDao.add(FRUIT, FRUIT_QUANTITY);
        int actual = Storage.storage.get(FRUIT);
        assertEquals(STORED_FRUIT_QUANTITY, actual);
    }

    @Test
    void get_Ok() {
        int actual = fruitDao.get(FRUIT);
        assertEquals(STORED_FRUIT_QUANTITY, actual);
    }
}
