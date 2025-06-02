package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.report.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private FruitsDao fruitsDao;
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        fruitsDao = new FruitsDaoImpl();
        reportService = new ReportServiceImpl(fruitsDao);

        // Очистити сховище перед кожним тестом
        fruitsDao.getAllFruits().clear();
    }

    @Test
    void reportAllFruits_shouldReturnHeaderOnly_whenStorageIsEmpty() {
        String report = reportService.reportAllFruits();
        String expected = "fruit,quantity" + System.lineSeparator();

        assertEquals(expected, report);
    }

    @Test
    void reportAllFruits_shouldReturnReportWithOneFruit() {
        fruitsDao.addFruit("apple", 10);

        String report = reportService.reportAllFruits();
        String expected = "fruit,quantity" + System.lineSeparator() + "apple,10";

        assertEquals(expected, report);
    }

    @Test
    void reportAllFruits_shouldReturnReportWithMultipleFruits() {
        fruitsDao.addFruit("apple", 10);
        fruitsDao.addFruit("banana", 20);
        fruitsDao.addFruit("orange", 5);

        String report = reportService.reportAllFruits();

        // Перевірка, що перший рядок - це заголовок
        assertTrue(report.startsWith("fruit,quantity" + System.lineSeparator()));

        // Перевірка, що кожен фрукт і кількість є в звіті
        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,20"));
        assertTrue(report.contains("orange,5"));

        // Перевірка кількості рядків (1 заголовок + 3 фрукти)
        String[] lines = report.split(System.lineSeparator());
        assertEquals(4, lines.length);
    }

    @Test
    void reportAllFruits_shouldReturnCorrectReport() {
        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", 10);
        Storage.fruitStorage.put("apple", 5);

        String report = reportService.reportAllFruits();

        String expectedHeader = "fruit,quantity";
        assertTrue(report.startsWith(expectedHeader), "Report should start with header");
        assertTrue(report.contains("banana,10"), "Report should contain banana line");
        assertTrue(report.contains("apple,5"), "Report should contain apple line");
    }
}
