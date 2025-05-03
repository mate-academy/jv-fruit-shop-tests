package core.basesyntax.service;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String TITLE_FOR_RESULT = "fruit,quantity";
    private static final String PUNCTUATION_MARK = ",";
    private static final ReportService reportService = new ReportServiceImpl();

    @BeforeClass
    public static void createReportString_ok() {
        String firstFruit = "orange";
        int quantityFirstFruit = 20;

        String secondFruit = "watermelon";
        int quantitySecondFruit = 15;

        String expected = TITLE_FOR_RESULT + System.lineSeparator() + firstFruit + PUNCTUATION_MARK
                + quantityFirstFruit + System.lineSeparator() + secondFruit + PUNCTUATION_MARK
                + quantitySecondFruit;
        Map<String, Integer> reportMap = new HashMap<>();
        reportMap.put(firstFruit, quantityFirstFruit);
        reportMap.put(secondFruit, quantitySecondFruit);
        String actual = reportService.generateReport(reportMap);
        assertEquals(expected, actual);
    }

    @Test
    public void mapIsEmpty_NotOk() {
        String firstFruit = "orange";
        int quantityFirstFruit = 20;

        String secondFruit = "watermelon";
        int quantitySecondFruit = 15;
        Map<String, Integer> reportMap = new HashMap<>();
        reportMap.put(firstFruit, quantityFirstFruit);
        reportMap.put(secondFruit, quantitySecondFruit);
        assertFalse(reportMap.isEmpty());
    }
}
