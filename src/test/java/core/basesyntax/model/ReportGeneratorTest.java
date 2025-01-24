package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        Storage.getStorage().clear();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void emptyStorageIsOk() {
        String expected = "fruit,quantity";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void nonEmptyStorageIsOk() {
        Storage.getStorage().put("Apple", 10);
        Storage.getStorage().put("Banana", 5);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "Apple,10" + System.lineSeparator()
                + "Banana,5";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void singleItemStorageIsOk() {
        Storage.getStorage().put("Orange", 3);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "Orange,3";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
