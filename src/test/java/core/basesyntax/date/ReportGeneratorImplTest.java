package core.basesyntax.date;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @Before
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void getReport_Ok() {
        Map<String, Integer> storage = new HashMap<>();

        storage.put("banana", 20);
        storage.put("apple", 3);
        storage.put("avocado", 34);

        String expected = "banana,20\napple,3\navocado,34";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }

    @Test
    public void getReport_emptyStorage_NotOk() {
        String expected = "";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }

    @Test
    public void getReport_nullStorage_NotOk() {
        reportGenerator = new ReportGeneratorImpl(null);

        String expected = "";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }

    @Test
    public void getReport_singleEntry_Ok() {
        Map<String, Integer> storage = new HashMap<>();

        storage.put("apple", 12);
        String expected = "apple,12";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }

    @Test
    public void getReport_sameKey_multipleValue_Ok() {
        Map<String, Integer> storage = new HashMap<>();

        storage.put("apple", 12);
        storage.put("apple", 10);

        String expected = "apple,10";
        String actual = reportGenerator.getReport();

        assertEquals(expected, actual);
    }
}
