package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.domain.FruitTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceImplTest {

    @Test
    @DisplayName("Report generating test")
    void reportGenerating_ok() {
        Storage.fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                                           FruitTransaction.FruitName.APPLE,
                                                           10));
        Storage.fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                                           FruitTransaction.FruitName.BANANA,
                                                           20));
        String expectedReport = "fruit,quantity"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator()
                + "banana,20"
                + System.lineSeparator();
        ReportService reportService = new ReportServiceImpl();
        String actualReport = reportService.generateReport();
        assertEquals(expectedReport, actualReport);
    }

}