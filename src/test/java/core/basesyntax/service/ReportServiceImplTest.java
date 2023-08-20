package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        FruitStorage.fruits.clear();
    }

    @Test
    void createReport_EmptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_validData_Ok() {
        FruitStorage.fruits.put("banana", new BigDecimal(152));
        FruitStorage.fruits.put("apple", new BigDecimal(90));
        String expected = "fruit,quantity" + System.lineSeparator()
                          + "banana,152" + System.lineSeparator()
                          + "apple,90";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
