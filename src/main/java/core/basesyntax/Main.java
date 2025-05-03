package core.basesyntax;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportMakerService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.DataParserServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.util.List;

public class Main {
    public static final String INPUT_PATH = "src/main/resources/input.csv";
    public static final String OUTPUT_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        ReaderService readerService = new ReaderServiceImpl();
        WriterService writerService = new WriterServiceImpl();
        FruitService fruitService = new FruitServiceImpl();
        DataParserService dataParserService = new DataParserServiceImpl();
        ReportMakerService reportMakerService = new ReportMakerServiceImpl();
        File file = new File(INPUT_PATH);
        List<String> dataFromFile = readerService.readData(file);
        List<FruitTransaction> parsedDataFromFile = dataParserService
                .parseData(dataFromFile);
        fruitService
                .calculateTotalQuantity(parsedDataFromFile);
        String report = reportMakerService.generateReport(FruitStorage.fruitStorage);
        writerService.writeData(report, OUTPUT_PATH);
    }
}
