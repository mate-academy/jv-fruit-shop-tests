package core.basesyntax;

import core.basesyntax.db.Storage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.impl.BalanceOperationHandler;
import core.basesyntax.operations.impl.PurchaseOperationHandler;
import core.basesyntax.operations.impl.ReturnOperationHandler;
import core.basesyntax.operations.impl.SupplyOperationHandler;
import core.basesyntax.service.DataProcessorService;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportGeneratorService;
import core.basesyntax.service.ReportWriterService;
import core.basesyntax.service.impl.DataProcessorServiceImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.PathValidatorImpl;
import core.basesyntax.service.impl.ReportGeneratorServiceImpl;
import core.basesyntax.service.impl.ReportWriterServiceImpl;

public class Main {
    private static final String INPUT_PATH = "src/main/java/core/basesyntax/resources/input.csv";
    private static final String OUTPUT_PATH = "src/main/java/core/basesyntax/resources/output.csv";

    public static void main(String[] args) throws IOException {
        Storage storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap =
                new HashMap<>();

        operationOperationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());

        FileReaderService fileReaderService = new FileReaderServiceImpl();
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationOperationHandlerMap);
        DataProcessorService dataProcessorService = new DataProcessorServiceImpl(operationStrategy);
        ReportGeneratorService reportGeneratorService = new ReportGeneratorServiceImpl();
        ReportWriterService reportWriterService = new ReportWriterServiceImpl();

        if (new PathValidatorImpl().filePathValidator(INPUT_PATH)) {
            dataProcessorService.process(fileReaderService.readFromFile(INPUT_PATH));
        } else {
            throw new IllegalArgumentException("Invalid file path: " + INPUT_PATH);
        }

        String report = reportGeneratorService.generateReport();

        reportWriterService.writeReportToFile(report, OUTPUT_PATH);

    }
}
