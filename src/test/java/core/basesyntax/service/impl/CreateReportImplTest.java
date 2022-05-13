package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.CreateReport;
import org.junit.After;
import org.junit.Test;

public class CreateReportImplTest {
    private final CreateReport createReport = new CreateReportImpl();
    private final FruitDao fruitDao = new FruitDaoImpl();

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_Ok() {
        fruitDao.update("banana", 59);
        fruitDao.update("apple", 14);
        String actual = createReport.createReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,59").append(System.lineSeparator())
                .append("apple,14");
        String expected = stringBuilder.toString();
        assertEquals(expected, actual);
    }
}
