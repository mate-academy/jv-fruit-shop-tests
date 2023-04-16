package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void returnCorrectString_Ok() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 100);
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,20"
                + System.lineSeparator() + "apple,100" + System.lineSeparator();
        String actual = reportService.report();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void returnNonFruitInStorage_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportService.report();
        Assertions.assertEquals(expected, actual);
    }
}
