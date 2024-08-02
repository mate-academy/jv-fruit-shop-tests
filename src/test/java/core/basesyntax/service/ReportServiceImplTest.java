package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.domain.FruitTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {

    private static final String HEADER_LINE = "fruit,quantity";
    private static final String EXPECTED_APPLE_REPORT = "apple,10";

    @Test
    @DisplayName("Report generating test")
    void reportGenerating_ok() {
        Storage.getFruitTransactions().put(
                FruitTransaction.FruitName.APPLE,
                10
        );
        String expectedReport = HEADER_LINE
                + System.lineSeparator()
                + EXPECTED_APPLE_REPORT
                + System.lineSeparator();
        ReportService reportService = new ReportServiceImpl();
        String actualReport = reportService.generateReport();
        assertEquals(expectedReport, actualReport);
        Storage.getFruitTransactions().clear();
    }
}
