package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.FruitShopService;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShopServiceImplTest {
    private ShopService shopService;
    private StorageDao storageDao;

    @Before
    public void setup() {
        storageDao = new StorageDaoImpl();
        shopService = new FruitShopService(storageDao);
    }

    @After
    public void cleanStorage() {
        Storage.storage.clear();
    }

    @Test
    public void getBalance_ok() {
        for (int i = 0; i < 10; i++) {
            storageDao.save("Fruit " + i, i);
        }
        Map<String, Integer> balance = shopService.getBalance();
        assertTrue(balance.size() == 10);
        for (int i = 0; i < 10; i++) {
            assertEquals(Integer.valueOf(i), storageDao.getQuantity("Fruit " + i));
        }
        Storage.storage.clear();
        for (int i = 0; i < 50; i++) {
            storageDao.save("apple", i);
            assertEquals(i, shopService.getBalance().get("apple").intValue());
        }
        Storage.storage.clear();
    }

    @Test
    public void getEmptyBalance_ok() {
        Map<String, Integer> emptyBalance = shopService.getBalance();
        assertTrue(emptyBalance.isEmpty());
    }
}
