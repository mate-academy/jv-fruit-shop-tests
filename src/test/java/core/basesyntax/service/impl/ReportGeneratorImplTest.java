package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final ReportGenerator reportGenerator = new ReportGeneratorImpl(storageDao);
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_APPLE = "apple";
    private static final String COMMA_SEPARATOR = ",";
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void generateReport_storageDaoIsNull_notOk() {
        ReportGenerator invalidReportGenerator = new ReportGeneratorImpl(null);
        assertThrows(NullPointerException.class, invalidReportGenerator::generateReport);
    }

    @Test
    void generateReport_storageIsEmpty_Ok() {
        String actual = reportGenerator.generateReport();
        assertEquals(REPORT_HEADER, actual);
    }

    @Test
    void generateReport_generateReport_Ok() {
        Map<String, Integer> data = Map.of(
                FRUIT_BANANA, 152,
                FRUIT_APPLE, 90
        );
        Storage.fruits.putAll(data);
        String expected = REPORT_HEADER + System.lineSeparator()
                + FRUIT_BANANA + COMMA_SEPARATOR + BANANA_QUANTITY + System.lineSeparator()
                + FRUIT_APPLE + COMMA_SEPARATOR + APPLE_QUANTITY;
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
