package core.basesyntax.data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(correctReportStringList, generatedReport);
    }

    @Test
    void generateFromIncorrectEmptyMap_NotOK() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> generator.generateReport(incorrectEmptyInventoryMap));
    }

    @Test
    void generateFromIncorrectNullMap_NotOK() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> generator.generateReport(incorrectNullInventoryMap));
    }
}
