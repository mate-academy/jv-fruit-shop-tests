package core.basesyntax.shop.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.shop.Fruit;
import core.basesyntax.shop.ShopService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;
    private static List<String> stringList;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruits.clear();
        shopService = new ShopServiceImpl();
        stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,apple,100");
    }

    @Test
    public void pushDataToStorage_OK() {
        shopService.pushDataToStorage(stringList);
        Map<Fruit, Integer> expect = new HashMap<>();
        expect.put(new Fruit("apple"), 100);
        assertEquals(Storage.getFruits(),expect);
    }
}
