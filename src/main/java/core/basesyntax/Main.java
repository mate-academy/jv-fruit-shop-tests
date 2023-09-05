package core.basesyntax;

import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.implementation.ParseServiceImpl;
import core.basesyntax.implementation.ReaderServiceImpl;
import core.basesyntax.implementation.ReportServiceImpl;
import core.basesyntax.implementation.WriterServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_READ = "src/main/resources/database";
    private static final String PATH_TO_WRITE = "src/main/resources/ReportFile";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationStrategyMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategyMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategyMap.put(Operation.RETURN, new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        ReaderService readerService = new ReaderServiceImpl();
        List<String> activities = readerService.readFromFile(PATH_TO_READ);
        ParseService parseService = new ParseServiceImpl();
        List<FruitTransaction> transactions = parseService.parseStringsToTransactions(activities);
        for (FruitTransaction transaction : transactions) {
            operationStrategy.get(transaction.getOperation()).handle(transaction);
        }
        ReportService reportService = new ReportServiceImpl();
        String report = reportService.createReport();
        WriterService writerService = new WriterServiceImpl();
        writerService.writeToFile(PATH_TO_WRITE, report);
    }
}
