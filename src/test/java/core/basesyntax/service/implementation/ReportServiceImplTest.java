package core.basesyntax.service.implementation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);
    private final ReportService reportService = new ReportServiceImpl(fruitService);

    @After
    public void after() {
        Storage.fruits.clear();
    }

    @Test
    public void getReport_Ok() {
        StringBuilder builder = new StringBuilder();
        Storage.fruits.put("banana", 50);
        Storage.fruits.put("apple", 20);
        builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,50")
                .append(System.lineSeparator())
                .append("apple,20");
        Assert.assertEquals(builder.toString(), reportService.getReport());
    }

    @Test
    public void getReport_EmptyFruitStorage_Ok() {
        StringBuilder builder = new StringBuilder("fruit,quantity");
        Assert.assertEquals(builder.toString(), reportService.getReport());
    }
}
