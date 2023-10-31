package core.basesyntax.strategy.serviceintrface.operation.model;

import core.basesyntax.actions.Balance;
import core.basesyntax.actions.Purchase;
import core.basesyntax.actions.Return;
import core.basesyntax.actions.Supply;
import core.basesyntax.db.Storage;
import core.basesyntax.serviceimpl.readerimpl.ReaderImpl;
import core.basesyntax.serviceimpl.readerimpl.WriterImpl;
import core.basesyntax.serviceimpl.transactionimpl.FruitTransactionParserImpl;
import core.basesyntax.serviceimpl.transactionimpl.FruitTransactionsHandlerImpl;
import core.basesyntax.serviceimpl.transactionimpl.ReportGenerateServiceImpl;
import core.basesyntax.strategy.serviceintrface.operation.OperationStrategy;
import core.basesyntax.strategy.serviceintrface.reader.Reader;
import core.basesyntax.strategy.serviceintrface.reader.Writer;
import core.basesyntax.strategy.serviceintrface.transaction.FruitTransactionHandler;
import core.basesyntax.strategy.serviceintrface.transaction.ReportGenerateService;
import core.basesyntax.strategy.serviceintrface.transaction.TransactionParser;
import java.util.List;

/**
 * Feel free to remove this class and create your own.
 */
public class Main {
    public static void main(String[] args) {
        // Initialization
        Storage storage = new Storage();
        Reader fileReader = new ReaderImpl();
        TransactionParser transactionParser = new FruitTransactionParserImpl();
        OperationStrategy operationStrategy = new OperationStrategy(
                new Balance(),
                new Purchase(),
                new Return(),
                new Supply());

        // Processing
        List<String> transactions = fileReader
                .readDataFromFile("src\\main\\resources\\dataFruit.csv");
        List<FruitTransaction> fruitTransactions = transactionParser
                .parseTransactions(transactions);

        FruitTransactionHandler fruitTransactionsHandler =
                new FruitTransactionsHandlerImpl(storage,operationStrategy);

        fruitTransactionsHandler.processTransactionsList(fruitTransactions);

        //Generate report

        ReportGenerateService reportGenerateService = new ReportGenerateServiceImpl(storage);
        String generateReport = reportGenerateService.generateReport();

        //Writing report

        Writer writeReportToFile = new WriterImpl();
        writeReportToFile.writeReportToFile(generateReport, "src\\main\\resources\\report.csv");

    }
}
