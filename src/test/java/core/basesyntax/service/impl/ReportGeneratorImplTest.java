package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String HEADERS_ONLY_REPORT = "fruit, amount" + System.lineSeparator();
    private static final String REPORT_OK =
            "fruit, amount" + System.lineSeparator() + "banana,20";
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_emptyStorage_ok() {
        String actualReport = reportGenerator.getReport();
        assertEquals(HEADERS_ONLY_REPORT, actualReport);
    }

    @Test
    void getReport_filledStorage_ok() {
        Storage.save("banana", 20);
        String actualReport = reportGenerator.getReport();
        assertEquals(REPORT_OK, actualReport);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
