package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.handlers.BalanceHandlerImpl;
import core.basesyntax.handlers.PurchaseHandlerImpl;
import core.basesyntax.handlers.ReturnHandlerImpl;
import core.basesyntax.handlers.SupplyHandlerImpl;
import core.basesyntax.impl.CsvReportServiceImpl;
import core.basesyntax.impl.FileWriterServiceImpl;
import core.basesyntax.impl.FruitTransactionServiceImpl;
import core.basesyntax.impl.ReaderServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/fruits.csv";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        ReaderService readerService = new ReaderServiceImpl();
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        List<String> inputDataList = readerService.readFromFile(INPUT_FILE_PATH);
        FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl();
        List<FruitTransaction> fruitTransactionList = fruitTransactionService
                .parseFruitTransactions(inputDataList);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        for (FruitTransaction fruitTransaction : fruitTransactionList) {
            OperationHandler operationHandler = operationStrategy
                    .getOperationHandler(fruitTransaction.getOperation());
            operationHandler.handle(fruitTransaction);
        }
        ReportService reportService = new CsvReportServiceImpl();
        String report = reportService.createReport(Storage.fruitsStorage);
        FileWriterService writerService = new FileWriterServiceImpl();
        writerService.writeToFile(report, OUTPUT_FILE_PATH);
    }
}
