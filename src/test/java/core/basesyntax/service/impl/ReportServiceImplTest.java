package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static String expectedReport;
    private static FruitDao fruitDao;
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void create_emptyStorage_Ok() {
        expectedReport = "Storage is empty";
        String actualReport = reportService.create();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void create_notEmptyStorage_Ok() {
        Storage.fruitsStorage.put("apple",5);
        expectedReport = "fruit,quantity" + System.lineSeparator() + "apple,5";
        assertEquals(expectedReport, reportService.create());
    }
}
