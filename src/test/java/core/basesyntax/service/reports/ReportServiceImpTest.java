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
    private static String header;
    private static Map<String, Integer> storage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportServiceImp = new ReportServiceImp(new FruitDaoImpl());
        header = "fruit,quantity";
        storage = FruitStorage.storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
    }

    @Test
    public void reportService_validStore_ok() {
        storage.put("banana",152);
        storage.put("apple", 90);
        String expectedValue = new StringBuffer().append(header)
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90").toString();
        String actualValue = reportServiceImp.create();
        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test
    public void reportService_nullStore_ok() {
        storage = null;
        String expectedValue = header;
        String actualValue = reportServiceImp.create();
        Assert.assertEquals(actualValue, expectedValue);
    }
}
