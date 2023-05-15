package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void initiateStorage() {
        Storage.fruitStorage.put("test1", 1);
    }

    @Test
    public void replaceValue_replace_Ok() {
        fruitDao.replaceValue("test1", 2);
        Integer expected = 2;
        Integer actual = Storage.fruitStorage.get("test1");
        assertEquals(expected, actual);
    }

    @Test
    public void replaceValue_addNew_Ok() {
        fruitDao.replaceValue("test2", 2);
        Integer expected = 2;
        Integer actual = Storage.fruitStorage.get("test2");
        assertEquals(expected, actual);
    }

    @Test
    public void getQuantity_get_Ok() {
        Integer expected = 1;
        Integer actual = fruitDao.getQuantity("test1");
        assertEquals(expected, actual);
    }

    @Test
    public void getQuantity_getInvalidElement_Ok() {
        Integer actual = fruitDao.getQuantity("null");
        assertNull(actual);
    }

    @Test
    public void getMap_get_Ok() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("test1", 1);
        Map<String, Integer> actualMap = fruitDao.getMap();
        assertEquals(expectedMap, actualMap);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
