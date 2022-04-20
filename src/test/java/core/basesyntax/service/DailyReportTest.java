package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class DailyReportTest {
    private final DailyReport dailyReport = new DailyReport();
    private final Map<String, Integer> fruitMap = new HashMap<>();

    {
        fruitMap.put("banana", 152);
        fruitMap.put("apple", 90);
    }

    @Test
    public void returnListFromMap_Ok() {
        List<String> actual = dailyReport.listOperation(fruitMap);
        List<String> expcted = Arrays.asList("banana,152", "apple,90");
        assertEquals(expcted,actual);
    }

    @Test (expected = NullPointerException.class)
    public void nullStorage_Ok() {
        dailyReport.listOperation(null);
    }
}
