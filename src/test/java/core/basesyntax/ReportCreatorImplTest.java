package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        Storage.getMap().put("banana", 130);
        Storage.getMap().put("apple", 999);
        Storage.getMap().put("pineapple", 12);
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void createReport_validCase_Ok() {
        String actual = reportCreator.createReport();
        Assertions.assertEquals(
                ("banana,130"
                + System.lineSeparator()
                        + "apple,999"
                        + System.lineSeparator()
                        + "pineapple,12"), actual);
    }
}
