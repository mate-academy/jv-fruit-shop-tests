import dao.TransactionDaoImpl;
import dao.TransactionsDao;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import service.CsvParseService;
import service.CsvReadService;
import service.CsvReportGenerator;
import service.CsvTransactionService;
import service.CsvWriteService;
import strategy.BalanceHandler;
import strategy.OperationStrategyImpl;
import strategy.PurchaseHandler;
import strategy.ReturnHandler;
import strategy.SupplyHandler;
import strategy.TransactionHandler;

public class FruitStore {
    
    private static final String OUTPUT_FILE_NAME = "outputFile";
    private static final String INPUT_FILE_NAME = "inputFile";
    
    public static void main(String[] args) {
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler());
        
        OperationStrategyImpl operationStrategyImpl = new OperationStrategyImpl(operationHandlers);
        TransactionsDao transactionDao = new TransactionDaoImpl();
        CsvParseService csvParseService = new CsvParseService();
        CsvReadService csvReadService = new CsvReadService();
        CsvTransactionService csvTransactionService =
                new CsvTransactionService(transactionDao,
                        operationStrategyImpl);
        CsvReportGenerator csvReportGenerator = new CsvReportGenerator();
        CsvWriteService csvWriteService = new CsvWriteService();
        
        String inputFilePath = Paths.get("src",
                        "main",
                        "resources",
                        INPUT_FILE_NAME)
                .toString();
        String outputFilePath = Paths.get("src",
                        "main",
                        "resources",
                        OUTPUT_FILE_NAME)
                .toString();
        
        List<String> lines = csvReadService.readTransactionsFromCsv(inputFilePath);
        List<FruitTransaction> transactions = csvParseService.parseTransactions(lines);
        
        csvTransactionService.processCsv(transactions);
        
        csvWriteService.writeReport(outputFilePath,
                csvReportGenerator.generateReport());
    }
}
