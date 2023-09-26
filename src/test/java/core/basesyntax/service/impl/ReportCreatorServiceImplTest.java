package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeAll
    static void beforeAll() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    void createReport_createdCorrectReport_Ok() {
        Storage.STORAGE.put("banana", 152);
        Storage.STORAGE.put("apple", 90);
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        String expected = builder.toString();
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }
}
