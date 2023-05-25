package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_WithNullInitializeList_notOk() {
        List<String> records = null;
        assertThrows(NullPointerException.class, () -> reportService.createReport(records));
    }

    @Test
    void createReport_WithEmptyInitializeList_ok() {
        List<String> records = Collections.emptyList();
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportService.createReport(records);
        assertEquals(expected, actual);
    }

    @Test
    void createReport_WithValidInformationInList_ok() {
        List<String> records = List.of("banana,10", "apple,15");
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();
        String actual = reportService.createReport(records);
        assertEquals(expected, actual);
    }
}

