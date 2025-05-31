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

public class ReportServiceTest {
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
    void reportAllFruits_handlesZeroQuantity() {
        fruitsDao.addFruit("mango", 0);
        String report = reportService.reportAllFruits();

        assertTrue(report.contains("mango,0"));
    }

    @Test
    void reportAllFruits_includesAllFruitsInReport() {
        fruitsDao.addFruit("apple", 10);
        fruitsDao.addFruit("banana", 20);
        fruitsDao.addFruit("orange", 30);

        String report = reportService.reportAllFruits();

        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,20"));
        assertTrue(report.contains("orange,30"));
    }

    @Test
    void reportAllFruits_returnsOnlyHeader_whenStorageIsEmpty() {
        String report = reportService.reportAllFruits();
        assertEquals("fruit,quantity", report.trim());
    }

    @Test
    void reportAllFruits_handlesSingleFruitCorrectly() {
        fruitsDao.addFruit("kiwi", 17);
        String report = reportService.reportAllFruits();

        String[] lines = report.split(System.lineSeparator());
        assertEquals(2, lines.length);
        assertEquals("fruit,quantity", lines[0]);
        assertEquals("kiwi,17", lines[1]);
    }

    @Test
    void reportAllFruits_trimsWhitespaceInOutput() {
        fruitsDao.addFruit("  peach ", 22);
        String report = reportService.reportAllFruits();

        // очікується саме такий формат з пробілами в назві
        assertTrue(report.contains("  peach ,22"));
    }

    @Test
    void reportAllFruits_preservesOrderOfInsertion() {
        fruitsDao.addFruit("a", 1);
        fruitsDao.addFruit("b", 2);
        fruitsDao.addFruit("c", 3);

        String report = reportService.reportAllFruits();
        int indexA = report.indexOf("a,1");
        int indexB = report.indexOf("b,2");
        int indexC = report.indexOf("c,3");

        assertTrue(indexA < indexB && indexB < indexC);
    }

    @Test
    void reportAllFruits_handlesVeryLargeQuantities() {
        fruitsDao.addFruit("dragonfruit", Integer.MAX_VALUE);
        String report = reportService.reportAllFruits();
        assertTrue(report.contains("dragonfruit," + Integer.MAX_VALUE));
    }
}
