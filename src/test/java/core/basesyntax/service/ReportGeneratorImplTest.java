package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @Test
    void shouldGenerateCorrectReport_whenMultipleFruitsAreInStorage() {
        Map<String, Integer> testMap = new LinkedHashMap<>();
        testMap.put("banana", 100);
        testMap.put("apple", 200);

        ReportGenerator reportGenerator = new ReportGeneratorImpl(testMap);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana, 100" + System.lineSeparator()
                + "apple, 200" + System.lineSeparator();
        assertEquals(expectedReport, reportGenerator.getReport());

    }

}
