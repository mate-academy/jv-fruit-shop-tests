package core.basesyntax.shop.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.shop.service.operations.Balance;
import core.basesyntax.shop.service.operations.OperationHandler;
import core.basesyntax.shop.service.operations.Purchase;
import core.basesyntax.shop.service.operations.Return;
import core.basesyntax.shop.service.operations.Supply;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ShopServiceTest {
    private static final String[] fruits = new String[]{"type,fruit,quantity",
            "s,banana,20"};

    @Test
    public void testShopServiceOK() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<String, OperationHandler> operationMap = new HashMap<>();
        operationMap.put("b", new Balance());
        operationMap.put("s", new Supply());
        operationMap.put("p", new Purchase());
        operationMap.put("r", new Return());
        ShopService shopService =
                new ShopServiceImpl(fruitDao, new OperationsStrategyImpl(operationMap));
        shopService.saveToStorage(fruits);
        Integer expected = 20;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

}
