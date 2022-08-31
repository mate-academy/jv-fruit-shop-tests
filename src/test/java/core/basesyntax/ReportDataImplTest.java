package core.basesyntax;

import core.basesyntax.service.ReportData;
import core.basesyntax.service.impl.ReportDataImpl;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportDataImplTest {
    private ReportData reportData;
    private Map<String, Integer> dataMap;

    @Before
    public void setUp() {
        reportData = new ReportDataImpl();
        dataMap = Map.of("apple", 150, "orange", 200);
    }

    @Test
    public void reportData_Ok() {
        StringBuilder builder = new StringBuilder("fruit,quantity")
                .append("\n").append("apple,150")
                .append("\n").append("orange,200");
        String expectedResult = builder.toString();
        String actualResult = reportData.createDataReport(dataMap.entrySet());
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void reportDataNull_NotOk() {
        reportData.createDataReport(null);
    }
}
