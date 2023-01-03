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

        String expected = TITLE_FOR_RESULT + NEW_LINE + firstFruit + PUNCTUATION_MARK
                + quantityFirstFruit + NEW_LINE + secondFruit + PUNCTUATION_MARK
                + quantitySecondFruit;

        Map<String, Integer> reportMap = new HashMap<>();
        reportMap.put(firstFruit, quantityFirstFruit);
        reportMap.put(secondFruit, quantitySecondFruit);
        String actual = reportService.getReportData(reportMap);

        assertEquals(expected, actual);
    }
}
