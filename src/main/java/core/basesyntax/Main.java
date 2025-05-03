package core.basesyntax;

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
import core.basesyntax.service.impl.TransactionProcessorImpl;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final FileReaderService readFileService = new FileReaderServiceImpl();
    private static final FruitTransactionParser fruitTransactionParser
            = new FruitTransactionParserImpl();
    private static final TransactionProcessor transactionProcessor
            = new TransactionProcessorImpl();
    private static final ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private static final FileWriterService writeFileService = new FileWriterServiceImpl();
    private static final String INPUT_FILE_PATH = "src/main/resources/input.csv";
    private static final String REPORT_FILE_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        String dataFromCsv = readFileService.readFromFile(Path.of(INPUT_FILE_PATH));
        List<FruitTransaction> transactions = fruitTransactionParser.parseTransactions(dataFromCsv);
        transactionProcessor.process(transactions);
        String report = reportGenerator.generateReport();
        writeFileService.writeToFile(report, Path.of(REPORT_FILE_PATH));
    }
}
