package basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import basesyntax.storage.Storage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
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
        List<String> expectedLines = Arrays.asList(
                "apple,15",
                "banana,10",
                "kiwi,5"
        );
        String actual = reportGenerator.getReport();
        List<String> actualLines = Arrays.asList(actual.split(System.lineSeparator()));
        assertEquals("fruit,quantity", actualLines.get(0));
        List<String> actualDataLines = actualLines.subList(1, actualLines.size());
        Collections.sort(expectedLines);
        Collections.sort(actualDataLines);
        assertEquals(expectedLines, actualDataLines);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
