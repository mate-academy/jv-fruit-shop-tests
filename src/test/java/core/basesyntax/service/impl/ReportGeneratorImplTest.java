package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final ReportGenerator generator = new ReportGeneratorImpl();
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String REPORT_HEADER = "fruit,quantity";

    @Test
    public void generateReport_EmptyStorage_Ok() {
        Map<Fruit, Integer> info = new HashMap<>();
        String expectedReport = REPORT_HEADER + LINE_SEPARATOR;
        String actualReport = generator.reportFromStorage(info);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void generateReport_OneItemInStorage_Ok() {
        Map<Fruit, Integer> info = new HashMap<>();
        info.put(new Fruit("apple"), 100);
        StringBuilder expectedReport = new StringBuilder();
        expectedReport.append(REPORT_HEADER)
                .append(LINE_SEPARATOR)
                .append("apple,100")
                .append(LINE_SEPARATOR);
        String actualReport = generator.reportFromStorage(info);
        assertEquals(expectedReport.toString(), actualReport);
    }

    @Test
    public void generateReport_TwoItemsInStorage_Ok() {
        Map<Fruit, Integer> info = new HashMap<>();
        info.put(new Fruit("apple"), 100);
        info.put(new Fruit("orange"), 50);
        StringBuilder expectedReport = new StringBuilder();
        expectedReport.append(REPORT_HEADER)
                .append(LINE_SEPARATOR)
                .append("apple,100")
                .append(LINE_SEPARATOR)
                .append("orange,50")
                .append(LINE_SEPARATOR);
        String actualReport = generator.reportFromStorage(info);
        assertEquals(expectedReport.toString(), actualReport);
    }
}
