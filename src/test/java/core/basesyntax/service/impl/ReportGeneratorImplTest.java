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
        String expected = "fruit,quantity";
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_generateReport_Ok() {
        Map<String, Integer> data = Map.of(
                "banana", 152,
                "apple", 90
        );
        Storage.fruits.putAll(data);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
