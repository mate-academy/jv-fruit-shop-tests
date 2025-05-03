package core.basesyntax;

import core.basesyntax.db.ReportStorage;
import core.basesyntax.db.TransactionStorage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.GenerateReportDataService;
import core.basesyntax.service.data.ProcessDataService;
import core.basesyntax.service.file.FileReadService;
import core.basesyntax.service.file.FileWriteService;
import core.basesyntax.service.file.WriteDataToStorageService;
import core.basesyntax.service.impl.FileReadServiceImpl;
import core.basesyntax.service.impl.FileWriteServiceImpl;
import core.basesyntax.service.impl.GenerateReportDataImpl;
import core.basesyntax.service.impl.ProcessDataImpl;
import core.basesyntax.service.impl.WriteDataToStorageImpl;
import core.basesyntax.strategy.AdditionHandler;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.SubtractionHandler;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final WriteDataToStorageService writeDataService = new WriteDataToStorageImpl();
    private static final FileReadService fileReadService
            = new FileReadServiceImpl(writeDataService);
    private static final FileWriteService fileWriteService = new FileWriteServiceImpl();
    private static final GenerateReportDataService generateReportData
            = new GenerateReportDataImpl();
    private static final ReportStorage reportStorage = new ReportStorage();
    private static final TransactionStorage transactionStorage = new TransactionStorage();
    private final Map<Operation, OperationHandler> operationsMap = new HashMap<>();

    public static void main(String[] args) {
        String fileInput = "src/resources/input.csv";
        String fileOut = "src/resources/out.csv";
        Main application = new Main();
        application.generateReportToFile(fileInput, fileOut);
    }

    private void generateReportToFile(String fileInput, String fileOut) {
        strategyInit();
        OperationStrategy operationStrategy = new OperationStrategy(operationsMap);
        ProcessDataService processData = new ProcessDataImpl(operationStrategy);
        fileReadService.readDataFromFile(fileInput);
        processData.process(transactionStorage.getAll());
        String report = generateReportData.report(reportStorage.getAll());
        fileWriteService.writeDataToFile(report, fileOut);
    }

    private void strategyInit() {
        operationsMap.put(Operation.BALANCE, new BalanceHandler());
        operationsMap.put(Operation.RETURN, new AdditionHandler());
        operationsMap.put(Operation.SUPPLY, new AdditionHandler());
        operationsMap.put(Operation.PURCHASE, new SubtractionHandler());
    }
}
