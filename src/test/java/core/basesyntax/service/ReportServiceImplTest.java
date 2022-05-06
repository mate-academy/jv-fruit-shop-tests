package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    @Test
    public void getReport_Ok() {
        Storage.fruits.clear();
        FruitDao fruitDao = new FruitDaoImpl();
        Fruit fruitFirst = new Fruit();
        String firstName = "apple";
        int firstAmount = 5;
        fruitFirst.setName(firstName);
        fruitFirst.setAmount(firstAmount);
        fruitDao.addFruit(fruitFirst);
        Fruit fruitSecond = new Fruit();
        String secondName = "banana";
        int secondAmount = 10;
        fruitSecond.setName(secondName);
        fruitSecond.setAmount(secondAmount);
        fruitDao.addFruit(fruitSecond);
        ReportService reportService = new ReportServiceImpl(fruitDao);
        List<String> report = reportService.getReport();
        List<String> expectedReport = List.of(
                "fruit,quantity",
                "apple,5",
                "banana,10");
        for (int i = 0; i < report.size(); i++) {
            Assert.assertEquals(expectedReport.get(i), report.get(i));
        }
    }
}
