package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ShopService;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static ShopService<String> shopService;

    @BeforeClass
    public static void beforeClass() {
        shopService = new ShopServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_storageIsEmpty_ok() {
        List<String> expected = List.of("fruit,quantity");
        assertEquals(expected, shopService.createReport());
    }

    @Test
    public void createReport_storageContainsOneElement_ok() {
        Storage.fruits.put(new Fruit("pineapple"), 70);
        List<String> expected = List.of("fruit,quantity", "pineapple,70");
        assertEquals(expected, shopService.createReport());
    }

    @Test
    public void createReport_storageContainsManyElements_ok() {
        Storage.fruits.put(new Fruit("banana"), 100);
        Storage.fruits.put(new Fruit("apple"), 50);
        Storage.fruits.put(new Fruit("orange"), 200);
        Storage.fruits.put(new Fruit("pineapple"), 70);
        List<String> expected = List.of("fruit,quantity",
                                        "banana,100",
                                        "apple,50",
                                        "orange,200",
                                        "pineapple,70");
        assertEquals(expected, shopService.createReport());
    }
}
