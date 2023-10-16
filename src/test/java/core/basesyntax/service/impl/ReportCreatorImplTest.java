package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void createReport_dbEmpty_notOk() {
        Storage.DB.clear();
        Assertions.assertThrows(RuntimeException.class,() -> reportCreator.createReport(),
                "If DB is Empty should be Exception");
    }
    
    @Test
    void createReport_dbSize_Ok() {
        Storage.DB.put("banane",99);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banane,99" + System.lineSeparator();
        String actual = reportCreator.createReport();
        Assertions.assertEquals(expected,actual,
                "Result String is not equals current String");
    }
}
