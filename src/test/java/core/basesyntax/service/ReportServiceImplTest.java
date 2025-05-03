package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static FruitDao fruitDao;

    private static void createFruit(String name, int amount) {
        Fruit fruit = new Fruit();
        fruit.setName(name);
        fruit.setAmount(amount);
        fruitDao.addFruit(fruit);
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void getReport_Ok() {
        Storage.fruits.clear();
        String firstName = "apple";
        int firstAmount = 5;
        createFruit(firstName, firstAmount);
        String secondName = "banana";
        int secondAmount = 10;
        createFruit(secondName, secondAmount);
        ReportService reportService = new ReportServiceImpl(fruitDao);
        List<String> report = reportService.getReport();
        List<String> expectedReport = List.of(
                "fruit,quantity",
                "apple,5",
                "banana,10");
        Assert.assertEquals("Test failed! Expected size of List: " + expectedReport.size(),
                expectedReport.size(), report.size());
        for (int i = 0; i < report.size(); i++) {
            Assert.assertEquals("Test failed! The line №: " + i + " is not as expected",
                    expectedReport.get(i), report.get(i));
        }
    }
}
