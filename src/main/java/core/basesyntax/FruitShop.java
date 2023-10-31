package core.basesyntax;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.StrategyProcessor;
import core.basesyntax.service.TransactionConverter;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.CsvDataReader;
import core.basesyntax.service.impl.FruitReportCreator;
import core.basesyntax.service.impl.FruitTransactionConverter;
import core.basesyntax.service.impl.StorageStrategyProcessor;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.List;

/**
 * Feel free to remove this class and create your own.
 */
public class FruitShop {
    public static final String INPUT_FILE_PATH = "src/main/resources/input.csv";
    public static final String OUTPUT_FILE_PATH = "src/main/resources/fruitReport.csv";

    public static void main(String[] args) {

        ReaderService readerService = new CsvDataReader();
        List<String[]> readFromFile = readerService.readFromFile(INPUT_FILE_PATH);

        TransactionConverter fr = new FruitTransactionConverter();
        List<FruitTransaction> listFruitTransaction = fr.convertToTransactionList(readFromFile);

        Storage storage = new FruitStorage();

        StrategyProcessor strategyProcessor = new StorageStrategyProcessor(storage);
        strategyProcessor.processTransactionStrategies(listFruitTransaction);

        ReportCreator reportService = new FruitReportCreator(storage);
        String fruitReportService = reportService.createReport();

        WriterService writerService = new WriterServiceImpl();
        writerService.writeToFile(fruitReportService, OUTPUT_FILE_PATH);
    }
}
