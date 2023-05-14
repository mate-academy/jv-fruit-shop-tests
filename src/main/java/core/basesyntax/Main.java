package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Activity;
import core.basesyntax.service.ActivityService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ActivityServiceImpl;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import core.basesyntax.service.impl.CsvReportServiceImpl;
import core.basesyntax.service.impl.CsvWriterServiceImpl;
import core.basesyntax.strategy.ActivityHandler;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/report.csv";
    private static final int LEGEND_LINE = 1;
    private static final Map<Activity.Operation, ActivityHandler> activityHandlerMap =
            Map.of(Activity.Operation.BALANCE, new BalanceHandlerImpl(),
                    Activity.Operation.PURCHASE, new PurchaseHandlerImpl(),
                    Activity.Operation.RETURN, new ReturnHandlerImpl(),
                    Activity.Operation.SUPPLY, new SupplyHandlerImpl());

    public static void main(String[] args) {
        //Read data from file
        ReaderService readerService = new CsvReaderServiceImpl();
        List<String> dataFromFile = readerService.readFile(INPUT_FILE_PATH);

        //Convert data to Activity objects and collect them to list
        ActivityService activityService = new ActivityServiceImpl();
        List<Activity> activities = dataFromFile.stream()
                .skip(LEGEND_LINE)
                .map(activityService::parse)
                .collect(Collectors.toList());

        //Process all activity object in list by strategy pattern
        ActivityStrategy operationStrategy = new ActivityStrategyImpl(activityHandlerMap);
        for (Activity activity : activities) {
            ActivityHandler handler = operationStrategy.getHandler(activity.getOperation());
            handler.process(activity);
        }

        //Create report using date from storage
        ReportService reportService = new CsvReportServiceImpl();
        String report = reportService.getReport(Storage.storeItems);

        //Save report to destination file;
        WriterService writerService = new CsvWriterServiceImpl();
        writerService.writeToFile(OUTPUT_FILE_PATH, report);
    }
}
