package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static FruitService fruitService;

    @BeforeClass
    public static void initFruitService() {
        fruitService = new FruitServiceImpl(new FruitDaoImpl());
    }

    @After
    public void clearStorage() {
        Storage.fruitsMap.clear();
    }

    @Test
    public void update_dataMatch_Ok() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("apple",100);
        testMap.put("banana",20);
        testMap.put("apricot",54);
        testMap.put("pineapple",81);
        for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
            fruitService.update(entry.getKey(), entry.getValue());
        }
        Assert.assertEquals(Storage.fruitsMap,testMap);
    }

    @Test
    public void get_valueExist_Ok() {
        Storage.fruitsMap.put("orange",99);
        Storage.fruitsMap.put("mango",123);
        Assert.assertNotNull(fruitService.get("mango"));
    }

    @Test (expected = NoSuchElementException.class)
    public void get_valueNotExist_NotOk() {
        Storage.fruitsMap.put("kiwi",11);
        Storage.fruitsMap.put("pear",23);
        fruitService.get("lime");
    }

    @Test
    public void getAll_dataMatch_Ok() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("orange",43);
        testMap.put("banana",25);
        testMap.put("lime",67);
        testMap.put("pineapple",30);
        for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
            fruitService.update(entry.getKey(), entry.getValue());
        }
        Assert.assertEquals(fruitService.getAll(),testMap);
    }
}
