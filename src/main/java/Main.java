import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Reader;
import core.basesyntax.service.ReaderImpl;
import core.basesyntax.service.TransactionGenerateReportService;
import core.basesyntax.service.TransactionGenerateReportServiceImpl;
import core.basesyntax.service.TransactionProcessDataService;
import core.basesyntax.service.TransactionProcessDataServiceImpl;
import core.basesyntax.service.Writer;
import core.basesyntax.service.WriterImpl;
import core.basesyntax.service.transaction.BalanceTransactionHandler;
import core.basesyntax.service.transaction.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.ReturnTransactionHandler;
import core.basesyntax.service.transaction.SupplyTransactionHandler;
import core.basesyntax.service.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Reader reader = new ReaderImpl();
        List<String> fromFile = reader.readFromFile("src/main/java/core/basesyntax/db/source.csv");
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap =
                new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        TransactionProcessDataService transactionProcessDataService =
                new TransactionProcessDataServiceImpl(transactionHandlerMap);
        Map<String, Integer> fruitsCount =
                transactionProcessDataService.processData(fromFile);
        TransactionGenerateReportService transactionGenerateReportService
                = new TransactionGenerateReportServiceImpl();
        String report = transactionGenerateReportService.generateReport(fruitsCount);
        Writer writer = new WriterImpl();
        writer.writeToFile("src/main/java/core/basesyntax/db/destination.csv", report);
    }
}
