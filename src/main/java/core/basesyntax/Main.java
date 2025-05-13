package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.BalanceHandlerImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseHandlerImpl;
import core.basesyntax.strategy.ReturnHandlerImpl;
import core.basesyntax.strategy.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILE_INPUT = "src/main/resources/input.cvs";
    private static final String FILE_OUTPUT = "src/main/resources/output.cvs";
    private static final ReaderService readerService = new ReaderServiceImpl();
    private static final ParserService parserService = new ParserServiceImpl();
    private static final WriterService writerService = new WriterServiceImpl();
    private static final Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();

    public static void main(String[] args) {
        List<String> fileContent = readerService.readFromFile(FILE_INPUT);
        List<FruitTransaction> fruitTransactions = parserService.parseTransactions(fileContent);
        initMapOperationHandler();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FruitService fruitService = new FruitServiceImpl(operationStrategy);
        fruitService.processTransactions(fruitTransactions);
        ReportService reportService = new ReportService();
        String report = reportService.generateReport();
        writerService.writeToFile(FILE_OUTPUT, report);
    }

    private static void initMapOperationHandler() {
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
    }
}
