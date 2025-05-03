package core.basesyntax;

import core.basesyntax.impl.ReadFileImpl;
import core.basesyntax.impl.ReportServiceImpl;
import core.basesyntax.impl.TransactionParserImpl;
import core.basesyntax.impl.TransactionServiceImpl;
import core.basesyntax.impl.WriteToFileImpl;
import core.basesyntax.impl.operation.OperationStrategy;
import core.basesyntax.impl.operation.OperationStrategyImpl;
import core.basesyntax.impl.operation.operations.BalanceHandlerImpl;
import core.basesyntax.impl.operation.operations.OperationHandler;
import core.basesyntax.impl.operation.operations.PurchaseHandlerImpl;
import core.basesyntax.impl.operation.operations.ReturnHandlerImpl;
import core.basesyntax.impl.operation.operations.SupplyHandlerImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ReadFileService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.WriteToFileService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_INPUT_INFO_FILE = "src/main/resources/inputInfo.csv";
    private static final String PATH_TO_REPORT_FILE = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        ReadFileService readFileService = new ReadFileImpl();
        TransactionParser transactionParser = new TransactionParserImpl();
        TransactionService transactionService =
                new TransactionServiceImpl(operationStrategy);
        ReportService reportService = new ReportServiceImpl();
        WriteToFileService writeToFileService = new WriteToFileImpl();

        List<String> fileInfo = readFileService.readFile(PATH_TO_INPUT_INFO_FILE);
        List<FruitTransaction> fruitTransactions = transactionParser
                .parseTransactions(fileInfo);
        transactionService.processTransactions(fruitTransactions);
        String report = reportService.createReport();
        writeToFileService.writeToFile(PATH_TO_REPORT_FILE, report);
    }
}
