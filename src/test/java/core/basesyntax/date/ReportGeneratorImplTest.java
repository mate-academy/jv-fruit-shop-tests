package core.basesyntax.date;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 5);
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    public void getReport_Ok() {
        String expected = "banana,5\napple,10";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }

    @Test
    public void getReport_EmptyStorage_NotOk() {
        reportGenerator = new ReportGeneratorImpl(new HashMap<>());

        String expected = "";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }

    @Test
    public void getReport_SingleTransaction_NotOk() {
        Map<String, Integer> newStorage = new HashMap<>();

        newStorage.put("apple", 23);

        reportGenerator = new ReportGeneratorImpl(newStorage);

        String expected = "apple,23";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }
}
