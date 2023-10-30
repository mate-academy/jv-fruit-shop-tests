package core.basesyntax.serviceimpl.transactionimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.serviceintrface.transaction.ReportGenerateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGenerateServiceImplTest {
    private Storage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new Storage();
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
