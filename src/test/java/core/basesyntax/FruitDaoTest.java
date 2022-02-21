package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoTest {
    private static final String[] GRAPES = new String[]{"b", "grapes", "50"};
    private static final List<String[]> LIST_OF_DATA = new ArrayList<>();
    private static final FruitDao FRUIT_DAO = new FruitDaoImpl();
    private static final String KEY = "grapes";
    private static final Integer EXPECTED_VALUE = 50;

    @BeforeClass
    public static void beforeClass() {
        LIST_OF_DATA.add(GRAPES);
    }

    @After
    public void tearDown() {
        if (Storage.fruits.size() != 0) {
            Storage.fruits.clear();
        }
    }

    @Test
    public void updateStorage_addOneElement_ok() {
        int storageSizeBeforeTesting = Storage.fruits.size();
        Assert.assertEquals(0, storageSizeBeforeTesting);
        FRUIT_DAO.updateStorage(LIST_OF_DATA);
        int storageSizeAfterTesting = Storage.fruits.size();
        Assert.assertEquals(1, storageSizeAfterTesting);
        Assert.assertEquals(EXPECTED_VALUE, Storage.fruits.get(KEY));
    }

    @Test(expected = RuntimeException.class)
    public void updateStorage_addNull_notOk() {
        FRUIT_DAO.updateStorage(null);
    }

    @Test
    public void getDataFromStorage_notEmptyStorage_ok() {
        Storage.fruits.put(KEY, 20);
        String actualDataFromStorage = FRUIT_DAO.getDataFromStorage();
        String expected = KEY + ",20";
        Assert.assertEquals(expected, actualDataFromStorage);
    }

    @Test
    public void getDataFromStorage_EmptyStorage_ok() {
        Assert.assertEquals(0, Storage.fruits.size());
        Assert.assertNull(Storage.fruits.get(KEY));
    }
}
