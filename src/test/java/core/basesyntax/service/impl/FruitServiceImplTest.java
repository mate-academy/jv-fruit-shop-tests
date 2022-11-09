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

    @Test
    public void applyFruitTransactions_correctData_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction("b", 10, "apple"));
        boolean expected = false;
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.processOperation(fruitTransaction);
            expected = true;
        }
        Assert.assertTrue(expected);
    }
}
