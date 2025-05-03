package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.StorageStrategy;
import core.basesyntax.service.StorageSupplyService;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.StorageStrategyImpl;
import core.basesyntax.service.impl.StorageSupplyServiceImpl;
import core.basesyntax.strategy.AdditionHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SubtractionHandler;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String inputPath = "src/main/resources/input.csv";
    private static final String resultPath = "src/main/resources/report.csv";

    public static void main(String[] args) {
        //getting local storage to operate
        Storage localStorage = new Storage();

        //prepare map for strategies
        Map<String, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put("s", new AdditionHandler());
        operationHandlers.put("b", new AdditionHandler());
        operationHandlers.put("r", new AdditionHandler());
        operationHandlers.put("p", new SubtractionHandler());

        //prepare service to aggregate data
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        StorageStrategy storageStrategy = new StorageStrategyImpl(operationStrategy);

        //getting dataaggregating data to storage
        FileReaderService fileReadToStorage = new FileReaderImpl();
        StorageSupplyService localSupplyService = new StorageSupplyServiceImpl(localStorage);
        storageStrategy.saveAll(fileReadToStorage.read(inputPath), localSupplyService);

        //data is in Storage and we're transferring it to file
        ReportCreator reportCreator = new ReportCreatorImpl();
        File output = new File(resultPath);
        FileWriterService fileWriter = new FileWriterImpl();
        fileWriter.write(output, reportCreator.getReport(localStorage));
    }
}
