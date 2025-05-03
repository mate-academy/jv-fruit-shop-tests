package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceTest {
    private static ReportCreatorService reportCreator;
    private static FruitDao dao;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorServiceImpl();
        dao = new FruitDaoImpl();
    }

    @Test
    void createReport_defaultCase_ok() {
        StringBuilder builder = new StringBuilder("fruit,quantity").append(System.lineSeparator())
                .append("papaya").append(",").append("3").append(System.lineSeparator())
                .append("banana").append(",").append("6").append(System.lineSeparator())
                .append("apple").append(",").append("1").append(System.lineSeparator());
        dao.addFruit("apple",1);
        dao.addFruit("papaya",3);
        dao.addFruit("banana",6);
        String expected = builder.toString();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyStorage_ok() {
        assertDoesNotThrow(() -> reportCreator.createReport());
    }

    @AfterEach
    void tearDown() {
        Storage.getStock().clear();
    }
}
