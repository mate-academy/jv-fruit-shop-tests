package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Before
    public void setup() {
        shopService = new FruitShopService(new StorageDaoImpl());
    }

    @After
    public void cleanStorage() {
        Storage.storage.clear();
    }

    @Test
    public void getBalance_ok() {
        for (int i = 0; i < 10; i++) {
            Storage.storage.put("Fruit " + i, i);
        }
        Map<String, Integer> balance = shopService.getBalance();
        assertTrue(balance.size() == 10);
        for (int i = 0; i < 10; i++) {
            assertEquals(Integer.valueOf(i), Storage.storage.get("Fruit " + i));
        }
        Storage.storage.clear();
        for (int i = 0; i < 50; i++) {
            Storage.storage.put("apple", i);
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
