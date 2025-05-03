package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import java.util.List;

public class Main {
    private static final String INPUT_FILE_PATH_NAME = "src/main/resources/InputFile.csv";
    private static final String OUTPUT_FILE_PATH_NAME = "src/main/resources/DailyReport.csv";

    public static void main(String[] args) {
        List<String> dataFromFile = new FileReaderServiceImpl().readFromFile(INPUT_FILE_PATH_NAME);
        List<FruitTransaction> transactions = new FruitTransactionParserImpl()
                .transformToTransaction(dataFromFile);
        new TransactionProcessorImpl().process(transactions);
        String report = new ReportGeneratorImpl().generate();
        new FileWriterServiceImpl().writeToFile(OUTPUT_FILE_PATH_NAME, report);
    }
}
