package core.basesyntax.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private final List<String> correctReportStringList
            = List.of("fruit,quantity", "apple,123", "banana,321");
    private final Map<String, Integer> incorrectEmptyInventoryMap = Map.of();
    private final Map<String, Integer> incorrectNullInventoryMap = null;

    private final ReportGenerator generator = new ReportGenerator();

    @Test
    void generateFromCorrectMap_OK() {
        TreeMap<String, Integer> correctMap = new TreeMap<>();
        correctMap.put("apple", 123);
        correctMap.put("banana", 321);
        List<String> generatedReport = generator.generateReport(correctMap);
        assertEquals(correctReportStringList, generatedReport);
    }

    @Test
    void generateFromIncorrectEmptyMap_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> generator.generateReport(incorrectEmptyInventoryMap),
                "Error while generate report. Map cannot be null or empty!");
    }

    @Test
    void generateFromIncorrectNullMap_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> generator.generateReport(incorrectNullInventoryMap),
                "Error while generate report. Map cannot be null or empty!");
    }
}
