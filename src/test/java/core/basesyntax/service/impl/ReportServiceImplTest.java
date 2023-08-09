package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void createReport_regularReport_Ok() {
        Storage.fruitStorage.put("banana", 10);
        Storage.fruitStorage.put("apple", 123);
        Storage.fruitStorage.put("orange", 54);

        String actual = new ReportServiceImpl().createReport();
        String expected =
                "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "orange,54" + System.lineSeparator()
                + "apple,123" + System.lineSeparator();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyReport_NotOk() {
        String actual = new ReportServiceImpl().createReport();
        String expected =
                "fruit,quantity" + System.lineSeparator();

        Assertions.assertEquals(expected, actual);
    }

}
