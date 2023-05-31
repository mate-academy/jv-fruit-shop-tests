package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.DataParserServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import java.util.List;

public class Main {
    private static final String PATH_TO_INPUT_FILE = "src/main/resources/input.csv";
    private static final String PATH_TO_REPORT_FILE = "src/main/resources/report.csv";
    private static final CsvFileReaderService readFileService =
            new CsvFileReaderServiceImpl();
    private static final DataParserService fruitTransactionParser =
            new DataParserServiceImpl();
    private static final TransactionProcessor transactionProcessor =
            new TransactionProcessorImpl();
    private static final ReportGenerator reportGenerator =
            new ReportGeneratorImpl();
    private static final FileWriterService writeFileService =
            new FileWriterServiceImpl();

    public static void main(String[] args) {
        List<String> dataFromFile = readFileService.readFromFile(PATH_TO_INPUT_FILE);
        List<FruitTransaction> fruitTransactions =
                fruitTransactionParser.parseData(dataFromFile);
        transactionProcessor.process(fruitTransactions);
        String report = reportGenerator.generateReport();
        writeFileService.writeToFile(PATH_TO_REPORT_FILE, report);
    }
}
