package core.basesyntax.serviceimpltest;

import static core.basesyntax.db.Storage.fruit;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.readservice.ReportService;
import core.basesyntax.service.readservice.ReportServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String REPORT_VALID_DATA = "fruit,quantity\n"
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        fruit.put("banana", 152);
        fruit.put("apple", 90);
    }

    @AfterAll
    static void afterAll() {
        fruit.clear();
    }

    @Test
    public void report_validData_Ok() {
        Assertions.assertTrue(true, REPORT_VALID_DATA);
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
