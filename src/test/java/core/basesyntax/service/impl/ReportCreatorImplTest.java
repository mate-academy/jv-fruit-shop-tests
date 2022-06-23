package core.basesyntax.service.impl;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final String NOTATION = "fruit, quantity";
    private static ReportCreator reportCreator;
    private static ShopDao shopDao;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
        shopDao = new ShopDaoImpl();
    }

    @Test
    void createReport_Ok() {
        shopDao.add("lemon", 30);
        String expected = "fruit, quantity" + System.lineSeparator() + "lemon,30";
        String actual = reportCreator.createReport();
        Assert.assertEquals(actual, expected);
    }

    @Test
    void correctNotation_Ok() {
        String[] splitReport = reportCreator.createReport().split(System.lineSeparator());
        boolean actual = splitReport[0].equals(NOTATION);
        Assert.assertTrue(actual);
    }

    @Test
    void emptyReport_NotOk() {
        Assert.assertFalse(reportCreator.createReport().isBlank());
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
