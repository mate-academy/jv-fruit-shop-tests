package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitReport;
import core.basesyntax.service.FruitService;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class FruitReportImplTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final FruitService fruitService = new FruitServiceImpl(fruitDao);
    private static FruitReport fruitReport = new FruitReportImpl(fruitService);

    @BeforeAll
    static void setUp() {
        fruitReport = new FruitReportImpl(fruitService);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void getFruitReport_ok() {
        Storage.fruits.put("banana",47);
        Storage.fruits.put("apple",15);
        String actualResult = fruitReport.getReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,47").append(System.lineSeparator())
                .append("apple,15");
        String expectedResult = stringBuilder.toString();
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void getEmptyFruitReport_notOk() {
        String actualResult = fruitReport.getReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity");
        String expectedResult = stringBuilder.toString();
        assertEquals(expectedResult,actualResult);
    }
}
