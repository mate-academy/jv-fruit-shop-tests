package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReportMakerServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportMakerServiceTest {
    private static final String DEFAULT_FRUIT_APPLE = "apple";
    private static final String DEFAULT_FRUIT_BANANA = "banana";
    private static final String DEFAULT_FRUIT_ORANGE = "orange";
    private static final int DEFAULT_QUANTITY_5 = 5;
    private static final int DEFAULT_QUANTITY_3 = 3;
    private static final int DEFAULT_QUANTITY_10 = 10;
    private final ReportMakerServiceImpl reportMakerService = new ReportMakerServiceImpl();

    @Test
    public void generateReportText_oneItem_ok() {
        Map<String, Integer> info = new HashMap<>();
        info.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_5);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,5" + System.lineSeparator();
        String actual = reportMakerService.generateReportText(info);
        assertEquals(expected, actual);
    }

    @Test
    public void generateReportText_multipleItems_ok() {
        Map<String, Integer> info = new HashMap<>();
        info.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_5);
        info.put(DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_3);
        info.put(DEFAULT_FRUIT_ORANGE, DEFAULT_QUANTITY_10);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,3" + System.lineSeparator()
                + "orange,10" + System.lineSeparator()
                + "apple,5" + System.lineSeparator();
        String actualReport = reportMakerService.generateReportText(info);
        assertEquals(expectedReport, actualReport);
    }
}
