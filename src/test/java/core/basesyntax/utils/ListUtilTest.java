package core.basesyntax.utils;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.FruitStorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.strategy.impl.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListUtilTest {
    private ListUtil listUtil;
    private Map<String, Integer> storageMap;
    private StorageDao storageDao;

    @Before
    public void setUp() {
        FruitStorage fruitStorage = new FruitStorage();
        storageDao = new FruitStorageDao(fruitStorage);
        storageMap = fruitStorage.getStorage();
        listUtil = new ListUtil();
    }

    @Test
    public void processList() {
        List<String> content = new ArrayList<>();
        content.add("type,fruit,quantity");
        content.add("b,banana,20");
        content.add("b,apple,100");
        content.add("s,banana,100");
        content.add("p,banana,13");
        content.add("r,apple,10");
        content.add("p,apple,20");
        content.add("p,banana,5");
        content.add("s,banana,50");

        listUtil.processList(content, new TransactionStrategyImpl(
                new StrategyUtil().initStrategyMap(storageDao)));

        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);

        Map<String, Integer> actual = storageMap;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storageMap.clear();
    }
}
