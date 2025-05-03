package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportGenerateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGenerateServiceImplTest {
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
    }

    @Test
    void check_reportGenerateService_OK() {
        fruitStorage.updateFruitQuantity("banana", 50);
        ReportGenerateService reportGenerateService = new ReportGenerateServiceImpl(fruitStorage);
        String generateReport = reportGenerateService.generateReport();
        String expectedResult = "fruit,quantity" + System.lineSeparator() + "banana,50";
        Assertions.assertEquals(expectedResult, generateReport);
    }

    @Test
    void check_emptyStorage_OK() {
        ReportGenerateService reportGenerateService = new ReportGenerateServiceImpl(fruitStorage);
        String generateReport = reportGenerateService.generateReport();
        String expectedResult = "fruit,quantity";
        Assertions.assertEquals(expectedResult, generateReport);
    }

}
