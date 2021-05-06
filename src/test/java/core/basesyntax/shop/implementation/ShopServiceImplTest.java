package core.basesyntax.shop.implementation;

import static org.junit.Assert.assertTrue;

import core.basesyntax.shop.ShopService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;
    private static List<String> stringList;

    @BeforeClass
    public static void beforeClass() {
        shopService = new ShopServiceImpl();
        stringList = new ArrayList<>();
        stringList.add("b,apple,100");
    }

    @Test
    public void pushDataToStorage_OK() {
        assertTrue(shopService.pushDataToStorage(stringList));
    }
}
