package core.basesyntax.shopservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dbimitation.Storage;
import core.basesyntax.fruitsassortment.Fruit;
import core.basesyntax.shopdao.FruitDaoImpl;
import core.basesyntax.shopoperations.Balance;
import core.basesyntax.shopoperations.ListOfOperations;
import core.basesyntax.shopoperations.Purchase;
import core.basesyntax.shopoperations.Return;
import core.basesyntax.shopoperations.ShopBalanceOperation;
import core.basesyntax.shopoperations.Supply;
import core.basesyntax.shopstrategy.StrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class StoreServiceImplTest {
    private static StoreService storeService;
    private static List<String> dataFromFile;

    @Before
    public void setUp() {
        Map<ListOfOperations, ShopBalanceOperation> operationMap = new HashMap<>();
        operationMap.put(ListOfOperations.B, new Balance());
        operationMap.put(ListOfOperations.S, new Supply());
        operationMap.put(ListOfOperations.P, new Purchase());
        operationMap.put(ListOfOperations.R, new Return());
        storeService = new StoreServiceImpl(new StrategyImpl(operationMap), new FruitDaoImpl());
        dataFromFile = new ArrayList<>();
    }

    @Test
    public void addToStorage() {
        Storage.getFruits().clear();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,60");
        dataFromFile.add("b,apple,60");
        dataFromFile.add("p,apple,20");
        dataFromFile.add("p,banana,20");
        storeService.addToStorage(dataFromFile);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 40);
        expected.put(new Fruit("apple"), 40);
        assertEquals(expected, Storage.getFruits());
    }
}
