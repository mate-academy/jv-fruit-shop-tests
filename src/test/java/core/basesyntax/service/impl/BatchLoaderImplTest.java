package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoCsvImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitCrate;
import core.basesyntax.service.BatchLoader;
import core.basesyntax.service.activitiy.ActivityHandler;
import core.basesyntax.service.activitiy.ActivityType;
import core.basesyntax.service.activitiy.AddingHandler;
import core.basesyntax.service.activitiy.RemovingHandler;
import core.basesyntax.strategy.ActivitiesStrategy;
import core.basesyntax.strategy.ActivitiesStrategyImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BatchLoaderImplTest {
    private static BatchLoader batchLoader;
    private static StorageDao storageDao;
    private static ActivitiesStrategy activitiesStrategy;
    private static Map<String, ActivityHandler> activityHandlerMap;
    private static List<String> fileData;
    private static List<core.basesyntax.model.FruitCrate> shopStorage;
    private static Comparator<core.basesyntax.model.FruitCrate> fruitComparator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(ActivityType.BALANCE.getLetter(), new AddingHandler());
        activityHandlerMap.put(ActivityType.SUPPLY.getLetter(), new AddingHandler());
        activityHandlerMap.put(ActivityType.RETURN.getLetter(), new AddingHandler());
        activityHandlerMap.put(ActivityType.PURCHASE.getLetter(), new RemovingHandler());
        activitiesStrategy = new ActivitiesStrategyImpl(activityHandlerMap);
        storageDao = new StorageDaoCsvImpl();
        batchLoader = new BatchLoaderImpl(storageDao, activitiesStrategy);
        fileData = new ArrayList<>();
        shopStorage = new ArrayList<>(List.of(new FruitCrate("banana", 125),
                new FruitCrate("apple", 83)));
        fruitComparator = new Comparator() {
            @Override
            public int compare(Object fruitCrate1,
                               Object fruitCrate2) {
                if (((FruitCrate) fruitCrate1).getName()
                        .equals(((FruitCrate) fruitCrate2).getName())) {
                    return ((FruitCrate) fruitCrate1).getQuantity()
                            - ((FruitCrate) fruitCrate2).getQuantity();
                }
                return -1;
            }
        };
    }

    @Before
    public void setUp() {
        fileData.clear();
        fileData.addAll(List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "s,apple,13",
                "r,banana,10",
                "r,apple,20",
                "p,banana,5",
                "p,apple,50"));
    }

    @Test
    public void loadBatch_validFileData_Ok() {
        List<FruitCrate> expected = shopStorage;
        List<FruitCrate> actual = batchLoader.loadBatch(fileData);
        for (int i = 0; i < expected.size(); i++) {
            if (fruitComparator.compare(expected.get(i), actual.get(i)) != 0) {
                Assert.fail();
            }
        }
    }

    @Test
    public void loadBatch_emptyFileData_Ok() {
        List<FruitCrate> expected = Collections.emptyList();
        List<FruitCrate> actual = batchLoader.loadBatch(Collections.emptyList());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void loadBatch_nullFileData_notOk() {
        batchLoader.loadBatch(null);
    }

    @Test
    public void loadBatch_tooBigPurchaseQuantity_notOk() {
        fileData.set(7, "p,banana,500");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Not enough " + "banana" + " to sell");
        batchLoader.loadBatch(fileData);
    }

    @Test
    public void loadBatch_firstLineTypeIsPurchase_notOk() {
        fileData.set(1, "p,banana,1");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Not enough " + "banana" + " to sell");
        batchLoader.loadBatch(fileData);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
