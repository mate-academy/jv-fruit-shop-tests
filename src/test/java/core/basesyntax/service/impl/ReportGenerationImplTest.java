package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGeneration;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportGenerationImplTest {
    private Map<Fruit,Integer> map;
    private ReportGeneration reportGeneration;
    private StringBuilder testReport;

    @Before
    public void sutUp() {
        map = new HashMap<>();
        map.put(new Fruit("banana"), 132);
        map.put(new Fruit("apple"), 90);
        reportGeneration = new ReportGenerationImpl();
        testReport = new StringBuilder();
        testReport.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,132")
                .append(System.lineSeparator())
                .append("apple,90")
                .append(System.lineSeparator());
    }

    @Test
    public void rightGeneration_Ok() {
        String actual = reportGeneration.generateReport(map);
        String expected = testReport.toString();
        Assert.assertEquals(expected, actual);
    }
}
