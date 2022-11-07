package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.WriteToDB;
import core.basesyntax.strategy.DoActivities;
import core.basesyntax.strategy.strategy.impl.BalanceReadActivity;
import core.basesyntax.strategy.strategy.impl.PurchaseActivity;
import core.basesyntax.strategy.strategy.impl.ReturnActivity;
import core.basesyntax.strategy.strategy.impl.SupplyActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToDbFromListTest {
    private static final String SPLITERATOR = ",";
    private static Map<String, DoActivities> strategy;
    private static FruitStorageDao storageDao;
    private static List<String> data;
    private WriteToDB writer;

    @BeforeClass
    public static void beforeClass() {
        strategy = new HashMap<>();
        storageDao = new FruitStorageDaoImpl();
        strategy.put("b", new BalanceReadActivity(storageDao));
        strategy.put("s", new SupplyActivity(storageDao));
        strategy.put("p", new PurchaseActivity(storageDao));
        strategy.put("r", new ReturnActivity(storageDao));
        data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("p,banana,5");
        data.add("s,banana,50");
    }

    @Before
    public void setUp() {
        writer = new WriteToDbFromList(SPLITERATOR);
    }

    @Test
    public void writeToDb_spliteratorIsNull_notOk() {
        try {
            new WriteToDbFromList(null);
            Assert.fail("Expected RunTimeException");
        } catch (RuntimeException e) {
            assertEquals("Spliterator must be matched", e.getMessage());
        }
    }

    @Test
    public void writeToDb_dataIsNull_notOk() {
        try {
            writer.writeToDB(null, strategy);
            Assert.fail("Expected RunTimeException");
        } catch (RuntimeException e) {
            assertEquals("Data must be matched", e.getMessage());
        }
    }

    @Test
    public void writeToDb_strategyMapIsNull_notOk() {
        try {
            writer.writeToDB(data, null);
            Assert.fail("Expected RunTimeException");
        } catch (RuntimeException e) {
            assertEquals("Strategy must be matched", e.getMessage());
        }
    }

    @Test
    public void writeToDb_dataIsEmpty_notOk() {
        List<String> emptyData = new ArrayList<>();
        assertFalse(writer.writeToDB(emptyData, strategy));
        assertEquals(Collections.emptyMap(), Storage.storage);
    }

    @Test
    public void writeToDb_strategyIsEmpty_notOk() {
        assertFalse(writer.writeToDB(data, Collections.emptyMap()));
        assertEquals(Collections.emptyMap(), Storage.storage);
    }

    @Test
    public void writeToDb_ok() {
        assertTrue(writer.writeToDB(data, strategy));
        assertFalse(Storage.storage.isEmpty());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
