package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportServiceImpl reportService;
    private String expectedResult;

    @BeforeAll
    static void beforeAll(){
        reportService = new ReportServiceImpl();
    }

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
