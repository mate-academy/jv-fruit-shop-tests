package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String TITLE_FOR_RESULT = "fruit,quantity";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String PUNCTUATION_MARK = ",";
    private final ReportService reportService = new ReportServiceImpl();

    @Test
    public void createReportString_Ok() {
        String firstFruit = "orange";
        int quantityFirstFruit = 20;

        String secondFruit = "watermelon";
        int quantitySecondFruit = 15;

        StringBuilder expectedStringBuilder = new StringBuilder(TITLE_FOR_RESULT);
        expectedStringBuilder.append(NEW_LINE)
                .append(firstFruit)
                .append(PUNCTUATION_MARK)
                .append(quantityFirstFruit)
                .append(NEW_LINE)
                .append(secondFruit)
                .append(PUNCTUATION_MARK)
                .append(quantitySecondFruit);
        String expected = expectedStringBuilder.toString();

        Map<String, Integer> reportMap = new HashMap<>();
        reportMap.put(firstFruit, quantityFirstFruit);
        reportMap.put(secondFruit, quantitySecondFruit);
        String actual = reportService.getReportData(reportMap);

        assertEquals(expected, actual);
    }
}
