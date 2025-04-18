package report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.impl.ReportFormatter;

public class ReportFormatterTest {
    private static ReportFormatter reportFormatter;

    @BeforeEach
    void create() {
        reportFormatter = new ReportFormatter();
    }

    @Test
    void format_withValidStorage_returnsFormattedReport() {
        Map<String, Integer> storage = new LinkedHashMap<>();
        storage.put("banana", 100);
        storage.put("apple", 50);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        String actual = reportFormatter.format(storage);
        assertEquals(expected, actual);
    }

    @Test
    void format_withEmptyStorage_returnsOnlyHeader() {
        Map<String, Integer> storage = new LinkedHashMap<>();
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportFormatter.format(storage);
        assertEquals(expected, actual);
    }
}
