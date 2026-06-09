package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void validReport_Ok() {
        storage.put("apple", 34);
        storage.put("banana", 24);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actual = reportGenerator.getReport(storage);

        assertEquals(HEADER
                + System.lineSeparator()
                + "apple" + COMMA + "34"
                + System.lineSeparator()
                + "banana" + COMMA + "24", actual);
    }

    @Test
    public void nullInputData_NotOk() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> reportGenerator.getReport(null));
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void emptyStorage_NotOk() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> reportGenerator.getReport(storage));
        assertEquals("storage is empty", exception.getMessage());
    }
}
