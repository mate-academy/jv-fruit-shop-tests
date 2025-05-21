package basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import basesyntax.storage.Storage;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @Test
    void getReport_emptyReport_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_oneElementReport_Ok() {
        Storage.put("apple", 15);
        String expected = "fruit,quantity" + System.lineSeparator() + "apple," + 15
                + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_moreThenOneElement_Ok() {
        Storage.put("apple", 15);
        Storage.put("banana", 10);
        Storage.put("kiwi", 5);
        String header = "fruit,quantity";
        List<String> expectedLines = Arrays.asList(
                "apple,15",
                "banana,10",
                "kiwi,5"
        );
        String actual = reportGenerator.getReport();
        List<String> actualLines = Arrays.asList(actual.split(System.lineSeparator()));
        assertTrue(actualLines.contains(header));
        for (String expectedLine : expectedLines) {
            assertTrue(actualLines.contains(expectedLine));
        }
        assertEquals(expectedLines.size() + 1, actualLines.size());
    }
}
