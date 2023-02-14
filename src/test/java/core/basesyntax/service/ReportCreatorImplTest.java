package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorImplTest {
    private ReportCreator reportCreator;

    @Before
    public void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void createReport_validReport_Ok() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("banana", 30);
        String actual = reportCreator.createReport(fruits);
        assertEquals("fruit,quantity" + System.lineSeparator() + "banana,30", actual);
    }

    @Test
    public void createReport_MoreValidData_Ok() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("banana", 30);
        fruits.put("plum", 100);
        fruits.put("apple", 50);
        fruits.put("pineapple", 5);
        String actual = reportCreator.createReport(fruits);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "plum,100" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "pineapple,5";
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_nullReport_NotOk() {
        assertThrows(RuntimeException.class, () -> reportCreator.createReport(null));
    }

    @Test
    public void createReport_emptyReport_NotOk() {
        assertThrows(RuntimeException.class, () -> reportCreator.createReport(new HashMap<>()));
    }
}
