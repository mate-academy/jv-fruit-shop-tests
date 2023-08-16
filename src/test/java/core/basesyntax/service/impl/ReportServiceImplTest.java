package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportServiceImpl reportService = new ReportServiceImpl();
    private String expectedResult;

    @BeforeEach
    public void setUp() {
        FruitStorage.getFruits().clear();
        expectedResult = "fruit,quantity" + System.lineSeparator() + "banana,152"
                + System.lineSeparator();
        FruitStorage.addFruit("banana", 152);
    }

    @Test
    public void reportRight_Ok() {
        String actualResult = reportService.generateReport();
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
