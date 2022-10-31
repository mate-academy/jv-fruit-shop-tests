package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseHandler;
import core.basesyntax.service.operations.ReturnHandler;
import core.basesyntax.service.operations.SupplyHandler;
import core.basesyntax.service.process.FruitTransactionService;
import core.basesyntax.service.process.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.read.FileReader;
import core.basesyntax.service.read.FruitTransactionParser;
import core.basesyntax.service.read.impl.FileReaderImpl;
import core.basesyntax.service.read.impl.FruitTransactionParserImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.write.ReportService;
import core.basesyntax.service.write.WriterService;
import core.basesyntax.service.write.impl.ReportServiceImpl;
import core.basesyntax.service.write.impl.WriterServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static Map<FruitTransaction.Operation,
            OperationHandler> operationOperationsHandlerMap = new HashMap<>();
    private static StorageDao storageDao = new StorageDaoImpl();
    private static FileReader fileReader = new FileReaderImpl();
    private static ReportService reportService = new ReportServiceImpl();
    private static String CSV_SEPARATOR = ",";
    private static String OPERATION_TYPE_NAME = "type";
    private static String FRUIT_QUANTITY_NAME = "quantity";
    private static String FRUIT_NAME = "fruit";
    private static FruitTransactionParser fruitTransactionParser =
            new FruitTransactionParserImpl(CSV_SEPARATOR, OPERATION_TYPE_NAME,
                    FRUIT_QUANTITY_NAME, FRUIT_NAME);
    private static WriterService writerService = new WriterServiceImpl();
    private static OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationOperationsHandlerMap);
    private static FruitTransactionService fruitTransactionService =
            new FruitTransactionServiceImpl(operationStrategy);

    public static void main(String[] args) {
        operationOperationsHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(storageDao));
        operationOperationsHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(storageDao));
        operationOperationsHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(storageDao));
        operationOperationsHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(storageDao));

        String directoryPath = "src/main/java/resources/";
        String fromFileName = "input.csv";
        List<String> fileContent = fileReader.readFromFile(directoryPath, fromFileName);

        System.out.println(fileContent);
        List<FruitTransaction> transactions = fruitTransactionParser.parse(fileContent);
        fruitTransactionService.processTransactions(transactions);
        System.out.println(Storage.storage);
        String toFileName = "result.csv";
        String report = reportService.createReport();
        writerService.writeToFile(directoryPath, toFileName, report);
    }
}
