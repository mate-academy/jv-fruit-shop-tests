package core.basesyntax.reporter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReportGeneratorTest {
    private ReportGenerator reportGenerator;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new FruitDao();
        reportGenerator = new CsvReportGenerator(storageDao);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void createReport_expectedStorage_ok() {
        FruitStorage.fruitStorage.put("banana", 20);
        String expectedReport = "fruit,quantity" + System.lineSeparator() + "banana,20"
                + System.lineSeparator();
        String report = reportGenerator.createReport();
        assertEquals(expectedReport, report);
    }

    @Test
    void createReport_emptyStorage_notOk() {
        assertThrows(RuntimeException.class, () -> reportGenerator.createReport());
    }

}
