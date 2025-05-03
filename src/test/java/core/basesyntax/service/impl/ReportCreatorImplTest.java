package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static ReportCreator reportCreator;
    private static FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void createReport_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator() + "apple,30" + System.lineSeparator();
        fruitDao.add("banana", 20);
        fruitDao.add("apple", 30);
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createEmptyReport_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }
}
