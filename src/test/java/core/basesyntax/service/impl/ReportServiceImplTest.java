package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeEach
    void init() {
        Storage.fruitStorage.clear();
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_regularReport_Ok() {
        Storage.fruitStorage.put("banana", 10);
        Storage.fruitStorage.put("apple", 123);
        Storage.fruitStorage.put("orange", 54);

        String actual = reportService.createReport();
        String expected =
                "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "orange,54" + System.lineSeparator()
                + "apple,123" + System.lineSeparator();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyReport_NotOk() {
        String actual = reportService.createReport();
        String expected =
                "fruit,quantity" + System.lineSeparator();

        Assertions.assertEquals(expected, actual);
    }

}
