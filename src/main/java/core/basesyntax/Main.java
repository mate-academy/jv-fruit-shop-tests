package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.WriterService;
import core.basesyntax.serviceimpl.CsvReportServiceImpl;
import core.basesyntax.serviceimpl.FileReaderServiceImpl;
import core.basesyntax.serviceimpl.FileWriterServiceImpl;
import core.basesyntax.serviceimpl.TransactionsServiceImpl;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_INPUT_FILE = "src/main/resources/Input_file.csv";
    private static final String PATH_TO_OUTPUT_FILE = "src/main/resources/Output_file.csv";

    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler>
                operationOperationHandlerMap = new HashMap<>();
        operationOperationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());

        ReaderService readerService = new FileReaderServiceImpl();
        List<String> dataFromInputFile = readerService.readFromFile(PATH_TO_INPUT_FILE);

        TransactionService transactionService = new TransactionsServiceImpl();
        List<FruitTransaction> listOfFruitTransactions =
                transactionService.getlistOfFruitTransaction(dataFromInputFile);

        OperationStrategyImpl operationStrategy =
                new OperationStrategyImpl(operationOperationHandlerMap);
        for (FruitTransaction fruitTransaction : listOfFruitTransactions) {
            OperationHandler operationHandler =
                    operationStrategy.getOperation(fruitTransaction.getOperation());
            operationHandler.operation(fruitTransaction);
        }
        ReportService reportService = new CsvReportServiceImpl();
        String dataToWtite = reportService.createReport();

        WriterService writerService = new FileWriterServiceImpl();
        writerService.writeToFile(dataToWtite, PATH_TO_OUTPUT_FILE);
    }
}
