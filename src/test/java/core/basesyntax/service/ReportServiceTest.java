package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportServiceTest {
    private static String VALID_REPORT;
    private static FruitDao fruitDao = new FruitDaoImpl();
    private ReportService reportService = new ReportServiceImpl();

    @BeforeAll
    static void beforeAll() {
        fruitDao.add("банан", 152);
        fruitDao.add("яблуко", 90);
        StringBuilder stringBuilder = new StringBuilder("fruit,quantity");
        for (Map.Entry<String, Integer> stringIntegerEntry : fruitDao.get().entrySet()) {
            stringBuilder.append("\r\n").append(stringIntegerEntry.getKey())
                    .append(",").append(stringIntegerEntry.getValue());
        }
        VALID_REPORT = stringBuilder.toString();
    }

    @AfterAll
    static void afterAll() {
        Storage.fruits.clear();
    }

    @Test
    void getReport_valid_ok() {
        String expected = VALID_REPORT;
        String actual = reportService.getReport(fruitDao.get());
        assertEquals(expected, actual);
    }
}
