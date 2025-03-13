package core.basesyntax;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorTest {
    private Map<String, Integer> reportData;

    @Before
    public void setUp() {
        reportData = new HashMap<>();
        reportData.put("banana",152);
        reportData.put("apple",90);
    }

    @Test
    public void getReport_properMap_ok() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String res = reportGenerator.getReport(reportData);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        Assert.assertEquals(res, expected);
    }

    @Test
    public void getReport_emptyMap_ok() {
        reportData.clear();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String res = reportGenerator.getReport(reportData);
        String expected = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals(res, expected);
    }

    @Test
    public void getReport_emptyKeyMap_ok() {
        reportData.put(null,86);
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String res = reportGenerator.getReport(reportData);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "null,86" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        Assert.assertEquals(res, expected);
    }
}
