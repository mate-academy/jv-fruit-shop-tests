package core.basesyntax.service.reports;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImpTest {
    private static ReportServiceImp reportServiceImp;
    private static final String HEADER = "fruit,quantity";
    private static Map<String, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        reportServiceImp = new ReportServiceImp(new FruitDaoImpl());
        storage = FruitStorage.storage;
    }

    @Before
    public void setUp() {
        storage.clear();
    }

    @Test
    public void create_validStore_Ok() {
        storage.put("banana",152);
        storage.put("apple", 90);
        String expectedValue = new StringBuffer().append(HEADER)
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90").toString();
        String actualValue = reportServiceImp.create();
        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test
    public void reportService_nullStore_Ok() {
        storage = null;
        String expectedValue = HEADER;
        String actualValue = reportServiceImp.create();
        Assert.assertEquals(actualValue, expectedValue);
    }
}
