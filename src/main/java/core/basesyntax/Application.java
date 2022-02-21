package core.basesyntax;

import core.basesyntax.service.*;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.impl.OperationStrategyImpl;

import java.util.List;

public class Application {
    private static final String FILE_NAME_FROM = "src/main/resources/database.csv";
    private static final String FILE_NAME_TO = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceCsv();
        DataHandler dataHandler = new FruitDataHandler(new OperationStrategyImpl());
        ReportCreateService reportCreateService = new FruitReportCreateService();
        FruitShopService fruitService = new FruitShopServiceImpl(dataHandler, reportCreateService);
        FileWriterService fileWriterService = new FileWriterServiceImpl();

        List<String> dataFromFile = fileReaderService.readFromFile(FILE_NAME_FROM);
        String report = fruitService.makeDailyReport(dataFromFile);
        fileWriterService.writeToFile(report, FILE_NAME_TO);
    }
}
