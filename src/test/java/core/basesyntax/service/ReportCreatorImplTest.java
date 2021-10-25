package core.basesyntax.service;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final ReportCreator REPORT_CREATOR = new ReportCreatorImpl();
    private static final String COLUMN_NAMES = "fruit,quantity";

    @Test(expected = RuntimeException.class)
    public void nullInputMap_NotOk() {
        REPORT_CREATOR.createReport(null);
    }

    @Test
    public void createReport_Ok() {
        Map<String, Integer> fruitQuantityMap = new HashMap<>();
        fruitQuantityMap.put("apple", 20);
        fruitQuantityMap.put("banana", 30);
        StringBuilder expected = new StringBuilder(COLUMN_NAMES);
        for (Map.Entry entry : fruitQuantityMap.entrySet()) {
            expected.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(",")
                    .append(entry.getValue());
        }
        String actual = REPORT_CREATOR.createReport(fruitQuantityMap);
        Assert.assertEquals(expected.toString(), actual);
    }
}
