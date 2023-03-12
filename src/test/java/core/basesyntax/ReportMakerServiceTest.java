package core.basesyntax;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportMakerServiceTest extends FruitShopTest {
    @Test
    public void generateReportText_oneItem_ok() {
        Map<String, Integer> info = new HashMap<>();
        info.put("apple", 5);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,5" + System.lineSeparator();
        String actual = reportMakerService.generateReportText(info);
        assertEquals(expected, actual);
    }

    @Test
    public void generateReportText_multipleItems_ok() {
        Map<String, Integer> info = new HashMap<>();
        info.put("apple", 5);
        info.put("banana", 3);
        info.put("orange", 10);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,3" + System.lineSeparator()
                + "orange,10" + System.lineSeparator()
                + "apple,5" + System.lineSeparator();
        String actualReport = reportMakerService.generateReportText(info);
        assertEquals(expectedReport, actualReport);
    }
}
