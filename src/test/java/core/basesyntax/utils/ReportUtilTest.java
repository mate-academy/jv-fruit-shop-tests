package core.basesyntax.utils;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.FruitStorageDao;
import core.basesyntax.db.FruitStorage;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportUtilTest {
    private static final String CSV_OUTPUT_HEADER = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private ReportUtil reportUtil;
    private Map<String, Integer> storageMap;
    private StorageDao storageDao;

    @Before
    public void setUp() {
        FruitStorage storage = new FruitStorage();
        storageDao = new FruitStorageDao(storage);
        storageMap = storage.getStorage();
        reportUtil = new ReportUtil();
        storageMap.put(BANANA, 152);
        storageMap.put(APPLE, 90);
    }

    @Test
    public void generateReport_correctData_ok() {
        List<String> expected = List.of(CSV_OUTPUT_HEADER,
                BANANA + SEPARATOR + 152,
                APPLE + SEPARATOR + 90);
        List<String> actual = reportUtil.generateReport(storageDao);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storageMap.clear();
    }
}
