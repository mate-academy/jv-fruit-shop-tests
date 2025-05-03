package core.basesyntax;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.dao.DaoStorageImpl;
import core.basesyntax.servise.DataProcessorService;
import core.basesyntax.servise.ParserService;
import core.basesyntax.servise.ReaderService;
import core.basesyntax.servise.ReportMakerService;
import core.basesyntax.servise.WriterService;
import core.basesyntax.servise.impl.CsvFileReaderServiceImpl;
import core.basesyntax.servise.impl.CsvFileWriterServiceImpl;
import core.basesyntax.servise.impl.DataProcessorServiceImpl;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.servise.impl.ParserServiceImpl;
import core.basesyntax.servise.impl.ReportMakerServiceImpl;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.MapOfHandlersForStrategyImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.List;

public class Main {
    private static final String PATH_INFILE = "src/main/resources/file.csv";
    private static final String PATH_OUTFILE = "src/main/resources/report.csv";

    public static void main(String[] args) {
        ReaderService readerService = new CsvFileReaderServiceImpl();
        ParserService parsingFruits = new ParserServiceImpl();
        DaoStorage daoStorage = new DaoStorageImpl();
        MapOfHandlersForStrategy mapForStrategy =
                new MapOfHandlersForStrategyImpl(daoStorage);
        OperationStrategy strategy = new OperationStrategyImpl(mapForStrategy);
        DataProcessorService processingFruits = new DataProcessorServiceImpl(strategy);
        ReportMakerService reportMaker = new ReportMakerServiceImpl(daoStorage);
        WriterService writerService = new CsvFileWriterServiceImpl();

        List<String> inputList = readerService.readFromFile(PATH_INFILE);
        List<FruitTransaction> listOfTransactions = parsingFruits.parsingData(inputList);
        processingFruits.processingData(listOfTransactions);
        String report = reportMaker.generateReport();
        writerService.writeToFile(PATH_OUTFILE, report);
    }
}
