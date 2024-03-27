package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoImplTest {
    private StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        Storage.fruitsQuantity.clear();
    }

    @Test
    public void add_fruitWithQuantity_OK() {
        String fruitName = "banana";
        int quantity = 100;
        HashMap<String, Integer> result = storageDao.add(fruitName, quantity);
        assertEquals("The quantity of the fruit should be as expected",
                Integer.valueOf(quantity), result.get(fruitName));
    }

    @Test
    public void search_fruitWithQuantity_OK() {
        String fruitName = "banana";
        int quantity = 100;
        Storage.fruitsQuantity.put(fruitName, quantity);
        Integer result = storageDao.get(fruitName);
        assertEquals("The quantity of the existing fruit should be as expected",
                Integer.valueOf(quantity), result);
    }

    @Test
    public void search_fruitNotExist_notOK() {
        String fruitName = "apple";
        Integer result = storageDao.get(fruitName);
        assertNull("The quantity of the non-existing fruit should be null",
                result);
    }
}
