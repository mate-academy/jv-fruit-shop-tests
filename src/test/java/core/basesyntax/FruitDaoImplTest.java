package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDaoImplTest {
    private static final String FIRST_FRUIT_NAME = "banana";
    private static final String SECOND_FRUIT_NAME = "apple";
    private static final int FIRST_FRUIT_QUANTITY = 15;
    private static final int SECOND_FRUIT_QUANTITY = 10;
    private FruitDaoImpl fruitDao;

    @BeforeEach
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        Storage.inputData.clear();
    }

    @Test
    public void testPutAndGetByName_Success() {
        fruitDao.put(SECOND_FRUIT_NAME, SECOND_FRUIT_QUANTITY);
        fruitDao.put(FIRST_FRUIT_NAME, FIRST_FRUIT_QUANTITY);
        assertEquals(SECOND_FRUIT_QUANTITY, fruitDao.getByName(SECOND_FRUIT_NAME));
        assertEquals(FIRST_FRUIT_QUANTITY, fruitDao.getByName(FIRST_FRUIT_NAME));
    }

    @Test
    public void testGetAll_Success() {
        Map<String, Integer> testData = new HashMap<>();
        testData.put(SECOND_FRUIT_NAME, SECOND_FRUIT_QUANTITY);
        testData.put(FIRST_FRUIT_NAME, FIRST_FRUIT_QUANTITY);
        fruitDao.put(SECOND_FRUIT_NAME, SECOND_FRUIT_QUANTITY);
        fruitDao.put(FIRST_FRUIT_NAME, FIRST_FRUIT_QUANTITY);
        assertEquals(testData, fruitDao.getAll());
    }
}
