package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportGeneratorService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operation.impl.WarehouseOperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static OperationStrategy operationStrategy;
    private static ReportGeneratorService reportGeneratorService;
    private static final String WRONG_FILE_PATH = "google/output.xml";
    private static String report;
    private static final String OPERATION = "b";
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 10;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new WarehouseOperationStrategyImpl();
        fruitService = new FruitServiceImpl(operationStrategy);
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void createResultFile_invalidInputData_notOk() {
        report = reportGeneratorService.generateReport(Storage.FRUIT_STORAGE);
        fruitService.createResultFile(report, WRONG_FILE_PATH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createResultFile_nullReport_notOk() {
        fruitService.createResultFile(null, WRONG_FILE_PATH);
    }

    @Test
    public void applyFruit_correctData_ok() {
        boolean actual = false;
        FruitTransaction fruitTransactions = new FruitTransaction(OPERATION, QUANTITY, FRUIT);
        if (fruitTransactions != null) {
            operationStrategy.processOperation(fruitTransactions);
            actual = true;
        }
        Assert.assertTrue(actual);
    }

    @Test(expected = NullPointerException.class)
    public void applyFruitTransactions_nullData_notOk() {
        try {
            List<FruitTransaction> fruitTransactions = new ArrayList<>();
            FruitTransaction fruitTransaction = new FruitTransaction();
            fruitTransactions.add(fruitTransaction);
            fruitTransactions.forEach(operationStrategy::processOperation);
        } catch (NullPointerException e) {
            throw new NullPointerException("Null data is not correct");
        }
    }

    @Test
    public void applyFruitTransactions_correctData_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(OPERATION, QUANTITY, FRUIT));
        boolean expected = false;
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.processOperation(fruitTransaction);
            expected = true;
        }
        Assert.assertTrue(expected);
    }
}
