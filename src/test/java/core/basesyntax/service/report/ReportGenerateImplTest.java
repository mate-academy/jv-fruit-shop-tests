package core.basesyntax.service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGenerateImplTest {
    private ReportGenerate reportGenerate;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        reportGenerate = new ReportGenerateImpl();
        fruitDao = new FruitDaoImpl();
        fruitDao.add("apple", 100);
        fruitDao.add("banana", 20);
    }

    @Test
    void generateReport_correctStorage_ok() {
        String expected = "fruit,quantity\nbanana,20\napple,100";
        assertEquals(expected, reportGenerate.generateReport(Storage.storageFruit));
    }
}
