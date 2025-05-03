package core.basesyntax.service;

import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static ReportGenerator reportGenerator;
    private Map<String, Integer> reportData;

    @BeforeEach
    void setUp() {
        reportData = new HashMap<>();
        reportData.put("banana",152);
        reportData.put("apple",90);
    }

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_properMap_ok() {
        String res = reportGenerator.getReport(reportData);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        Assertions.assertEquals(res, expected);
    }

    @Test
    public void getReport_emptyMap_ok() {
        Map<String, Integer> emptyData = new HashMap<>();
        String res = reportGenerator.getReport(emptyData);
        String expected = "fruit,quantity" + System.lineSeparator();
        Assertions.assertEquals(res, expected);
    }

    @Test
    public void getReport_emptyKeyMap_ok() {
        reportData.put(null,86);
        String res = reportGenerator.getReport(reportData);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "null,86" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        Assertions.assertEquals(res, expected);
    }
}
