package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @Test
    void returnCorrectString_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.updateFruitQuantity("banana", 30);
        fruitDao.updateFruitQuantity("apple", 160);
        ReportGeneratorImpl<String, Integer> reportService =
                new ReportGeneratorImpl<>(fruitDao.getAllFruits());

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,160" + System.lineSeparator();

        Assertions.assertEquals(expected, reportService.getReport());
    }

    @Test
    void returnNonFruitInStorage_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = new ReportGeneratorImpl<>(fruitDao.getAllFruits())
                .getReport();
        Assertions.assertEquals(expected, actual);
    }
}
