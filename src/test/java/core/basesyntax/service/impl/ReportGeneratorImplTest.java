package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static FruitStorageDao fruitStorageDao;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        reportGenerator = new ReportGeneratorImpl(fruitStorageDao);
    }

    @Test
    void generateReport_checkReport_Ok() {
        FruitTransaction fruitTransaction1
                = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50);
        FruitTransaction fruitTransaction2
                = new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 30);
        OperationHandler operationHandler = new BalanceOperationHandler(fruitStorageDao);
        operationHandler.handleOperation(fruitTransaction1);
        operationHandler.handleOperation(fruitTransaction2);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "orange,30" + System.lineSeparator();
        assertEquals(expectedReport, reportGenerator.generateReport());
    }
}
