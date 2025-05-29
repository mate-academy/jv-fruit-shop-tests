package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.report.ReportService;
import core.basesyntax.service.report.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private FruitsDao fruitsDao;
    private ReportService reportService;

    @BeforeEach
    void setup() {
        Storage.fruitStorage.clear();
        fruitsDao = new FruitsDaoImpl();
        reportService = new ReportServiceImpl(fruitsDao);
    }

    @Test
    void reportAllFruits_returnsFormattedString() {
        fruitsDao.addFruit("apple", 100);
        fruitsDao.addFruit("banana", 50);
        String report = reportService.reportAllFruits();

        assertTrue(report.contains("apple,100"));
        assertTrue(report.contains("banana,50"));
    }

    @Test
    void reportAllFruits_emptyStorage_returnsEmptyString() {
        String report = reportService.reportAllFruits();
        assertEquals("", report);
    }

    @Test
    void reportAllFruits_singleFruit_reportIsSingleLine() {
        fruitsDao.addFruit("kiwi", 25);
        String report = reportService.reportAllFruits();

        assertEquals("kiwi,25", report.trim());
    }

    @Test
    void reportAllFruits_handlesZeroQuantity() {
        fruitsDao.addFruit("mango", 0);
        String report = reportService.reportAllFruits();

        assertTrue(report.contains("mango,0"));
    }
}
