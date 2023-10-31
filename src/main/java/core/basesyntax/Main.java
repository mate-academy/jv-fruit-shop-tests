package core.basesyntax;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.DataParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String filePath = "src/main/resources/test_file.csv";
    private static final String pathToSave = "src/main/resources/report_file.csv";

    public static void main(String[] args) {
        final ReaderService reader = new ReaderServiceImpl();
        final WriterService writer = new WriterServiceImpl();
        ReportServiceImpl reportService = new ReportServiceImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(getOperationMap());
        DataParserService separatorService = new DataParserServiceImpl();
        TransactionProcessor transactionProcessor = new TransactionProcessorImpl(operationStrategy);

        List<String> readLinesList = reader.readFile(filePath);
        List<Transaction> transactionsList = separatorService
                .getTransactionsList(readLinesList);
        transactionProcessor.processTransaction(transactionsList);
        writer.writeReportToFile(reportService.getReport(), pathToSave);
    }

    private static Map<Operation, OperationHandler> getOperationMap() {
        Map<Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(Operation.BALANCE, new BalanceHandler());
        operationMap.put(Operation.SUPPLY, new SupplyHandler());
        operationMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationMap.put(Operation.RETURN, new ReturnHandler());
        return operationMap;
    }
}
