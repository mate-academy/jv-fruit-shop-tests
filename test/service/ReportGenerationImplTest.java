import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGenerationImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ReportGenerationImplTest {
    private static Map<Fruit, Integer> testMap;
    private static StringBuilder testReport;
    private static ReportGenerator testReportGenerator;

    @BeforeClass
    public static void setUp() {
        testMap = new HashMap<>();
        testMap.put(new Fruit("banana"), 152);
        testMap.put(new Fruit("apple"), 90);
        testReportGenerator = new ReportGenerationImpl();
        testReport = new StringBuilder();
        testReport.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .append(System.lineSeparator());
    }

    @Test
    public void correctReportGeneration_Ok() {
        String actual = testReportGenerator.generateReport(testMap);
        String expected = testReport.toString();
        Assert.assertEquals(expected, actual);
    }
}

