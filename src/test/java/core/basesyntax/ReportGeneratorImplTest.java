package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.shopserviceandreportgenerator.ReportGenerator;
import core.basesyntax.shopserviceandreportgenerator.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    @Test
    public void getReport_shouldGenerateCorrectReport() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 30);
        storage.put("apple", 80);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        String expected = "fruit,quantity\n"
                + "banana,30\n"
                + "apple,80";

        String actual = reportGenerator.getReport(storage);

        assertEquals(expected, actual);
    }
}

