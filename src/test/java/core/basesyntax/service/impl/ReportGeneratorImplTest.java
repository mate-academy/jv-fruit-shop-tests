package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void beforeEach() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @Test
    void getReport_StorageWithFruits_Ok() {
        Storage.put(new Fruit("apple"),1);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,1" + System.lineSeparator();

        String actual = reportGenerator.getReport();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        Assertions.assertEquals(expected,reportGenerator.getReport());
    }
}
