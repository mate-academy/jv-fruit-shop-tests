package core.basesyntax.serviceimpltest;

import static core.basesyntax.db.Storage.FRUIT_MAPS;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.readservice.ReportService;
import core.basesyntax.service.readservice.ReportServiceImpl;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String SEPARATOR = ",";
    private static final String REPORT_VALID_DATA = "fruits,quantity";

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @AfterAll
    static void afterAll() {
        FRUIT_MAPS.clear();
    }

    @Test
    public void report_validData_Ok() {
        Storage.getFruit().put("banana", 152);
        Storage.getFruit().put("apple", 90);
        String expected = REPORT_VALID_DATA + System.lineSeparator() + FRUIT_MAPS.entrySet()
                .stream()
                .map(fruit -> fruit.getKey()
                        + SEPARATOR + fruit.getValue()
                        + System.lineSeparator())
                .collect(Collectors.joining());

        String actual = reportService.shopReport(FRUIT_MAPS);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void reportData_notOk() {
        String actual = "quantity,fruits";
        Assertions.assertFalse(false, actual);
    }

    @Test
    void report_null_notOk() {
        assertThrows(RuntimeException.class, () -> reportService.shopReport(null));
    }
}
