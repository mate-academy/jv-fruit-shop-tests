package core.basesyntax;

import static core.basesyntax.service.impl.CsvReportGeneratorImpl.REPORT_HEADER;
import static core.basesyntax.util.CsvUtil.CSV_SEPARATOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.CsvReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReportGeneratorTest {
    private static final FruitTransaction defaultTransaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void beforeEach() {
        Storage.clearFruits();
        reportGenerator = new CsvReportGeneratorImpl(new StorageDaoImpl());
    }

    @Test
    public void report_NullStorageDao_NotOk() {
        assertThrows(NullPointerException.class, () -> {
            reportGenerator = new CsvReportGeneratorImpl(null);
            reportGenerator.getReport();
        });
    }

    @Test
    public void report_EmptyStorage_Ok() {
        Storage.getAllFruits();
        assertDoesNotThrow(() -> assertEquals(REPORT_HEADER, reportGenerator.getReport()));
    }

    @Test
    public void report_StorageNullValues_Ok() {
        Storage.setFruitQuantity(null, null);
        assertDoesNotThrow(() -> assertEquals(REPORT_HEADER, reportGenerator.getReport()));
    }

    @Test
    public void report_CompleteValues_Ok() {
        Storage.setFruitQuantity(defaultTransaction.getFruit(), defaultTransaction.getQuantity());
        String expected = REPORT_HEADER + defaultTransaction.getFruit()
                + CSV_SEPARATOR + defaultTransaction.getQuantity()
                + System.lineSeparator();
        assertDoesNotThrow(() -> {
            String report = reportGenerator.getReport();
            assertEquals(expected, report);
        });
    }
}
