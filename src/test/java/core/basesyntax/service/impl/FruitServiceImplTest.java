package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportGeneratorService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operation.impl.WarehouseOperationStrategyImpl;
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
}
