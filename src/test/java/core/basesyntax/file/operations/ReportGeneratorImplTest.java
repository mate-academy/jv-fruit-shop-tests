package core.basesyntax.file.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.getFruitsStorage().clear();
    }

    @Test
    void generateReport_Ok() {
        fruitDao.saveOrUpdate("banana", 20);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,20" + System.lineSeparator();
        String actual = reportGenerator.generateReport();

        assertEquals(expected, actual);
    }

    @Test
    void generateReport_emptyData_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.generateReport();

        assertEquals(expected, actual);
    }
}
