package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private FruitShopDao fruitShopDao = new FruitShopDaoImpl();
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport() {
        Storage.storage.put("banana", 100);
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_ReturnZeroBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        String fruit = fruitTransaction.setFruit("apple");
        Integer amount = fruitTransaction.setAmount(0);
        fruitShopDao.add(fruit, amount);
        String expected = "fruit, quantity" + System.lineSeparator()
                + "apple,0" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
