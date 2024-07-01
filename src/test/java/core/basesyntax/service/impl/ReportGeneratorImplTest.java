package core.basesyntax.service.impl;

import static core.basesyntax.constants.Constants.APPLE;
import static core.basesyntax.constants.Constants.BANANA;
import static core.basesyntax.constants.Constants.EMPTY_DB_EXPECTED;
import static core.basesyntax.constants.Constants.NORMAL_QUANTITY;
import static core.basesyntax.constants.Constants.UPDATE_TEST_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        Storage.clearDb();
    }

    @Test
    void getReport_emptyDb_ok() {
        String report = reportGenerator.getReport();
        assertEquals(EMPTY_DB_EXPECTED, report);
    }

    @Test
    void getReport_singleEntryDb_ok() {
        Storage.updateDb(APPLE, NORMAL_QUANTITY);
        String actualReport = reportGenerator.getReport();
        String expectedReport = String.format("fruit,quantity%sapple,1", System.lineSeparator());
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getReport_multipleEntriesDb_ok() {
        Storage.updateDb(APPLE, NORMAL_QUANTITY);
        Storage.updateDb(BANANA, NORMAL_QUANTITY);
        String report = reportGenerator.getReport();
        List<String> reportLines = Arrays.asList(report.split(System.lineSeparator()));
        assertTrue(reportLines.contains("apple,1"));
        assertTrue(reportLines.contains("banana,1"));
    }

    @Test
    void getReport_updatesQuantity_ok() {
        Storage.updateDb(APPLE, NORMAL_QUANTITY);
        Storage.updateDb(APPLE, UPDATE_TEST_QUANTITY);
        String expectedReport = String.format("fruit,quantity%sapple,15", System.lineSeparator());
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }
}
