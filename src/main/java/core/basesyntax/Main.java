package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.CsvReaderService;
import core.basesyntax.service.CsvWriterService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitTransactionProcessorService;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import core.basesyntax.service.impl.CsvWriterServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.FruitTransactionProcessorServiceImpl;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FRUITS_FILE_NAME = "fruits.csv";
    private static final String REPORT_FILE_NAME = "report.csv";
    private static FruitService fruitService = new FruitServiceImpl(new FruitDaoImpl());

    public static void main(String[] args) {
        CsvReaderService csvReaderService = new CsvReaderServiceImpl();
        List<String> fruitsOperations = csvReaderService.read(Path.of(FRUITS_FILE_NAME));

        Map<String, OperationHandler> operationsHandlerMap = initOperationsHandlerMap();
        OperationHandlerStrategy operationHandlerStrategy =
                new OperationHandlerStrategyImpl(operationsHandlerMap);

        FruitTransactionProcessorService fruitTransactionProcessorService =
                new FruitTransactionProcessorServiceImpl(operationHandlerStrategy);
        fruitTransactionProcessorService.fillStorage(fruitsOperations);

        ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl(fruitService);
        String report = reportCreatorService.createReport();

        CsvWriterService csvWriterService = new CsvWriterServiceImpl();
        csvWriterService.write(Path.of(REPORT_FILE_NAME),report);
    }

    private static Map<String, OperationHandler> initOperationsHandlerMap() {
        Map<String, OperationHandler> operationsHandlerMap = new HashMap<>();
        operationsHandlerMap.put("b", new BalanceHandler(fruitService));
        operationsHandlerMap.put("s", new SupplyHandler(fruitService));
        operationsHandlerMap.put("r", new ReturnHandler(fruitService));
        operationsHandlerMap.put("p", new PurchaseHandler(fruitService));
        return operationsHandlerMap;
    }
}
