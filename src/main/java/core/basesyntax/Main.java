package core.basesyntax;

import core.basesyntax.checker.EntryValidator;
import core.basesyntax.db.InMemoryStorage; 
import core.basesyntax.model.Transaction;
import core.basesyntax.service.Converter;
import core.basesyntax.service.Reader;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.TransactionDao;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.ConverterCsvImpl;
import core.basesyntax.service.impl.CsvReaderImpl;
import core.basesyntax.service.impl.CsvWriterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.TransactionDaoImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseHandler;
import core.basesyntax.strategy.handler.ReturnHandler;
import core.basesyntax.strategy.handler.SupplyHandler;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Reader reader = new CsvReaderImpl();
        List<String> lines = reader.readFile("FileInput.csv");

        InMemoryStorage inMemoryStorage = new InMemoryStorage();
        EntryValidator entryValidator = new EntryValidator(inMemoryStorage);

        TransactionDao transactionDao = new TransactionDaoImpl(inMemoryStorage, entryValidator);
        Converter converter = new ConverterCsvImpl(transactionDao);

        List<Transaction> transactions = converter.convertCsvLines(lines);

        OperationHandler balanceHandler = new BalanceHandler(transactionDao);
        OperationHandler supplyHandler = new SupplyHandler(transactionDao);
        OperationHandler returnHandler = new ReturnHandler(transactionDao);
        OperationHandler purchaseHandler = new PurchaseHandler(transactionDao);

        Map<Transaction.Operation, OperationHandler> handlers = Map.of(
                Transaction.Operation.BALANCE, balanceHandler,
                Transaction.Operation.RETURN, returnHandler,
                Transaction.Operation.PURCHASE, purchaseHandler,
                Transaction.Operation.SUPPLY, supplyHandler
        );

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);

        ShopService shopService = new ShopServiceImpl(operationStrategy, transactionDao);

        shopService.process(transactions);
        Map<String, Integer> processedData = shopService.getData();

        ReportGenerator generator = new ReportGeneratorImpl();
        String report = generator.generateReport(processedData);

        Writer writer = new CsvWriterImpl();
        writer.writeToFile(report, "FileOutput.csv");
    }
}
