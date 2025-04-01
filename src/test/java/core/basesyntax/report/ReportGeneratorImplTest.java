package core.basesyntax.report;

import static core.basesyntax.db.Storage.getStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    public void clearReport() {
        getStorage().clear();
    }

    @Test
    public void getReport_validStorage_returnsFormattedReport() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 5);
        String report = reportGenerator.getReport(storage);
        assertEquals("fruit,quantity" + System.lineSeparator() + "banana,5"
                + System.lineSeparator() + "apple,10" + System.lineSeparator(), report);
    }

    @Test
    public void getReport_emptyStorage_returnsHeaderOnly() {
        String report = reportGenerator.getReport(new HashMap<>());
        assertEquals("fruit,quantity" + System.lineSeparator(), report);
    }

    @Test
    public void getReport_nullStorage_returnsHeaderOnly() {
        String report = reportGenerator.getReport(null);
        assertEquals("fruit,quantity" + System.lineSeparator(), report);
    }
}
