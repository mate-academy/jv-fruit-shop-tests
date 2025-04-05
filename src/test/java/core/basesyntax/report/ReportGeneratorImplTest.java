package core.basesyntax.report;

import static core.basesyntax.db.Storage.getStorage;
import static core.basesyntax.db.Storage.setStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static Map<String, Integer> storage;

    @BeforeAll
    public static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        storage = new HashMap<>();
    }

    @Test
    public void getReport_validStorage_returnsFormattedReport() {
        storage.put("apple", 10);
        storage.put("banana", 5);
        setStorage(storage);
        String report = reportGenerator.getReport(getStorage());
        assertEquals("fruit,quantity" + System.lineSeparator() + "banana,5"
                + System.lineSeparator() + "apple,10" + System.lineSeparator(), report);
    }
}
