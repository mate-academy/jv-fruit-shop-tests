package core.basesyntax.service;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorImplTest {
    @Test
    void testGetReportWithMultipleFruits() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("banana", 100);
        testMap.put("apple", 200);

        ReportGenerator reportGenerator = new ReportGeneratorImpl(testMap);
        String expectedReport = "fruit,quantity" + System.lineSeparator() +
                "banana,100" + System.lineSeparator() +
                "apple,200" + System.lineSeparator();
        assertEquals(expectedReport, reportGenerator.getReport(),
                "The report should match the expected format with multiple fruits");

    }

}