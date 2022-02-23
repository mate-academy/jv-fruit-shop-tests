package core.basesyntax.sevice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> transactionList;
    private static Map<String, Integer> expectedMap;
    private static FruitTransaction fruitTransaction1;
    private static FruitTransaction fruitTransaction2;

    @BeforeClass
    public static void beforeClass() {
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl());
        fruitTransaction1 = new FruitTransaction("s", "banana", 10);
        fruitTransaction2 = new FruitTransaction("r", "apple", 10);
        transactionList = new ArrayList<>();
        transactionList.add(fruitTransaction1);
        transactionList.add(fruitTransaction2);
        expectedMap = new HashMap<>();
        expectedMap.put(fruitTransaction1.getFruit(), fruitTransaction1.getQuantity());
        expectedMap.put(fruitTransaction2.getFruit(), fruitTransaction2.getQuantity());
        Storage.fruitStorage.clear();
    }

    @Test
    public void doFruitShopOperation_Ok() {
        fruitShopService.doFruitShopOperation(transactionList);
        assertEquals(expectedMap, Storage.fruitStorage);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
