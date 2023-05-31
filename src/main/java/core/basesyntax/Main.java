package core.basesyntax;

import static core.basesyntax.db.FruitStorage.fruits;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.strategy.TransactionProcessorStrategy;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        FruitTransactionParser fruitTransactionParser = new FruitTransactionParserImpl();
        TransactionProcessor transactionProcessor = new TransactionProcessorStrategy();
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        Map<String, Integer> database = fruits;

        List<String> dataFromFile = fileReaderService.readFromFile("src/main/resources/input.csv");

        List<FruitTransaction> transactions = fruitTransactionParser.toTransactions(dataFromFile);

        transactionProcessor.process(transactions, database);

        String report = reportGenerator.generateReport(database);

        fileWriterService.writeReportToFile(report, "src/main/resources/output.csv");
    }
}
