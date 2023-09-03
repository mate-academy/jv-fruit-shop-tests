package core.basesyntax.service.report;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final int HEADER_SIZE = 1;
    private ReportCreator reportCreator = new ReportCreatorImpl();

    @AfterEach
    void tearDown() {
        Storage.getFruitTypesAndQuantity().clear();
    }

    @Test
    void createReport_correctData_Ok() {
        Storage.getFruitTypesAndQuantity().put("banana", 50);
        String actualReport = reportCreator.createReport(Storage.getFruitTypesAndQuantity());
        int expectedSize = Storage.getFruitTypesAndQuantity().size() + HEADER_SIZE;
        int actualSize = actualReport.split(System.lineSeparator()).length;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createReport_null_Ok() {
        assertThrows(RuntimeException.class, () -> {
            reportCreator.createReport(null);
        });
    }

    @Test
    void createReport_negativeAmount_NotOk() {
        Storage.getFruitTypesAndQuantity().put("banana", -20);
        assertThrows(RuntimeException.class, () -> {
            reportCreator.createReport(Storage.getFruitTypesAndQuantity());
        });
    }
}
