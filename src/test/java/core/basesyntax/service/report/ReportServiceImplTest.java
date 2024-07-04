package core.basesyntax.service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportService reportService = new ReportServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void reportService_correctInput_isOk() {
        Storage.storage.put("banana", 30);
        StringBuilder str = new StringBuilder();
        str.append("fruit").append(",").append("quantity").append(System.lineSeparator())
                .append("banana").append(",").append(30).append(System.lineSeparator());

        String expected = str.toString();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void reportService_multipleItems_isOk() {
        Storage.storage.put("apple", 69);
        Storage.storage.put("banana", 96);

        StringBuilder str = new StringBuilder();
        str.append("fruit").append(",").append("quantity").append(System.lineSeparator())
                .append("banana").append(",").append(96).append(System.lineSeparator());
        str.append("apple").append(",").append(69).append(System.lineSeparator());

        String expected = str.toString();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }
}
