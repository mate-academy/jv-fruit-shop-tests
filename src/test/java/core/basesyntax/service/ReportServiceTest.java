package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService();
        Storage.getFruits().clear();
    }

    @Test
    void emptyStorage_ReturnOnlyHeader() {
        String expected = "fruit,quantity\n";
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void singleFruit_CorrectFormat() {
        Storage.getFruits().put("apple", 10);
        String expected = "fruit,quantity\napple,10\n";
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void multipleFruits_CorrectFormat() {
        Storage.getFruits().put("orange", 5);
        Storage.getFruits().put("apple", 10);

        String actual = reportService.generateReport();
        String expected = "fruit,quantity\n"
                + "orange,5\n"
                + "apple,10\n";

        assertEquals(expected, actual);
    }

    @Test
    void fruitWithZeroQuantity() {
        Storage.getFruits().put("orange", 0);
        String expected = "fruit,quantity\norange,0\n";
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }
}
