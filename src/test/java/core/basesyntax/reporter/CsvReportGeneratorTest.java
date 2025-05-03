package core.basesyntax.reporter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvReportGeneratorTest {
    private static FruitStorageDao fruitStorageDao;
    private static ReportGenerator csvReportGenerator;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        csvReportGenerator = new CsvReportGenerator(fruitStorageDao);
    }

    @Test
    void generateReport_withEmptyData_ok() {
        String expectedReport = "fruit,quantity";
        assertEquals(expectedReport, csvReportGenerator.generateReport());
    }

    @Test
    void generateReport_withNonEmptyData_ok() {
        fruitStorageDao.add("apple", 10);
        fruitStorageDao.add("banana", 5);

        String expectedReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,5"
                + System.lineSeparator()
                + "apple,10";
        assertEquals(expectedReport, csvReportGenerator.generateReport());
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitToStorageQuantityMap.clear();
    }
}
