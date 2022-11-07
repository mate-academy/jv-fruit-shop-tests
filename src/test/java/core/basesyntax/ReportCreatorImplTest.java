package core.basesyntax;

import core.basesyntax.servises.ReportCreator;
import core.basesyntax.servises.impl.ReportCreatorImpl;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String CORRECT_REPORT = "fruit,quantity\n"
            + "107,banana\n" + "100,apple\n";
    private static HashMap<String, Integer> testStorage;
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void setUp() {
        reportCreator = new ReportCreatorImpl();
        testStorage = new HashMap<String, Integer>();
        testStorage.put("banana", 20);
        testStorage.put("apple", 100);
        testStorage.put("banana", testStorage.get("banana") + 100);
        testStorage.put("banana", testStorage.get("banana") - 13);
    }

    @Test
    public void reportCreatorCorrectTest_Ok() {
        String report = reportCreator.createReport(testStorage);
        Assert.assertEquals(CORRECT_REPORT, report);
    }

    @Test(expected = RuntimeException.class)
    public void reportCreatorNullTest_NotOk() {
        reportCreator.createReport(null);
    }
}
