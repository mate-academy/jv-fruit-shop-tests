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
    private static final FileReaderService FILE_READ_SERVICE;
    private static final FruitTransactionParser FRUIT_TRANSACTION_PARSER;
    private static final TransactionProcessor TRANSACTION_PROCESSOR;
    private static final ReportGenerator REPORT_GENERATOR;
    private static final FileWriterService FILE_WRITE_SERVICE;

    static {
        FILE_READ_SERVICE = new FileReaderServiceImpl();
        FRUIT_TRANSACTION_PARSER = new FruitTransactionParserImpl();
        TRANSACTION_PROCESSOR = new TransactionProcessorImpl();
        REPORT_GENERATOR = new ReportGeneratorImpl();
        FILE_WRITE_SERVICE = new FileWriterServiceImpl();
    }

    public static void main(String[] args) {
        String dataFromFile =
                FILE_READ_SERVICE.readFromFile(Path.of("src/main/resources/input.csv"));
        List<FruitTransaction> transactions = FRUIT_TRANSACTION_PARSER.toTransactions(dataFromFile);
        TRANSACTION_PROCESSOR.process(transactions);
        String report = REPORT_GENERATOR.generate();
        FILE_WRITE_SERVICE.writeToFile(Path.of("src/main/resources/output.csv"), report);
    }
}
