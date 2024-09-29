package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final Map<Fruit, Integer> testMap = new HashMap<>();
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeAll
    static void beforeAll() {
        Storage.clear();
    }

    @Test
    void getReport_StorageWithFruits_Ok() throws NoSuchFieldException {
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
