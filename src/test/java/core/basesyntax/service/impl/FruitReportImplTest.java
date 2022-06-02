package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitReport;
import core.basesyntax.service.FruitService;
import org.junit.After;
import org.junit.Test;

public class FruitReportImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);
    private final FruitReport fruitReport = new FruitReportImpl(fruitService);

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void getReport_ok() {
        Storage.fruits.put("banana",47);
        Storage.fruits.put("apple",15);
        String actual = fruitReport.getReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,47").append(System.lineSeparator())
                .append("apple,15");
        String expected = stringBuilder.toString();
        assertEquals(expected,actual);
    }

    @Test
    public void getReport_emptyStorage_Ok() {
        String actual = fruitReport.getReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity");
        String expected = stringBuilder.toString();
        assertEquals(expected,actual);
    }
}
