package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static FruitStorageDao fruitStorageDao;
    private static ReportService reportService;
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();

    @BeforeAll
    public static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        reportService = new ReportServiceImpl(fruitStorageDao);
    }

    @Test
    public void generateReport_validStorage_ok() {
        fruitStorageDao.updateFruitQuantity("apple", 100);
        fruitStorageDao.updateFruitQuantity("banana", 200);
        String expectedReport = HEADER + "banana,200" + System.lineSeparator()
                + "apple,100";
        assertEquals(expectedReport, reportService.generateReport());
    }
}
